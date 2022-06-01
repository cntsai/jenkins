# Ansible

```bash
cd ansible

# check inventory (hosts)
$ ansible-inventory -i inventory --list

# check ansible can reach all hosts defined in inventory
$ ansible all -i inventory -m ping

# run ansible
$ ansible-playbook -i inventory playbook.yml
```
