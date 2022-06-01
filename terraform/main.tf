provider "aws" {
  region = var.region
}

locals {
  common_tags = {
    Name    = "aws_spot_instance"
    project = var.project_tag
  }
}

resource "aws_security_group" "aws_sg" {
  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = local.common_tags
}

resource  "aws_launch_template" "aws_lt" {
  name_prefix = "aws_image_launch_template"
  image_id    = var.ami_id
  key_name    = var.key_name

  vpc_security_group_ids = [
    aws_security_group.aws_sg.id,
  ]

  tag_specifications {
    # Tags of EC2 instances
    resource_type = "instance"
    tags = local.common_tags
  }

  tag_specifications {
    # Tags of EBS volumes
    resource_type = "volume"
    tags = local.common_tags
  }
}

resource "aws_ec2_fleet" "aws_ec2_fleet" {
  launch_template_config {
    launch_template_specification {
      launch_template_id = aws_launch_template.aws_lt.id
      version            = "1"
    }

    override {
      availability_zone = var.zone
      instance_type     = var.instance_type

      # Maximum price per unit hour that you are willing to pay for a Spot Instance.
      max_price = var.bid_price
    }
  }

  target_capacity_specification {
    # Valid values: on-demand, spot.
    default_target_capacity_type = var.target_capacity_type
    total_target_capacity        = 1
  }

  # Terminate instances for an EC2 Fleet if it is deleted successfully.
  terminate_instances = true

  # Terminate running instances when the EC2 Fleet request expires or gets
  # cancelled.
  terminate_instances_with_expiration = true

  # The tags of the Fleet resource itself.
  # To tag instances at launch, specify the tags in the Launch Template.
  tags = local.common_tags

}
