#!/bin/bash

DIR="$(cd -- "$( dirname -- "${BASH_SOURCE[0]:-$0}"; )" &> /dev/null && pwd 2> /dev/null;)";
PROGRAM="$1"
JENKINS="${DIR}/jenkins"
TAG="sifive/jenkins:latest"

info() {
  echo -e "[$(date +'%Y-%m-%dT%H:%M:%S%z')][info] $*"
}

err() {
  echo -e "[$(date +'%Y-%m-%dT%H:%M:%S%z')][error] $*" >&2
}

has_docker() {
  cmd="docker"
  if ! command -v "${cmd}" > /dev/null; then
    err "please install docker before docker build."
    return 1
  fi
}

build() {
  local jenkins="${1}"
  local tag="${2}"
  local img="$(echo "${tag}" | cut -d ":" -f 1)"

  cd "${jenkins}"

  if docker images | grep -q "${img}"; then
    err "please remove old image first"
    return 1
  fi

  if ! docker build -t "${tag}" .; then
    err "docker build ${jenkins} failed"
    return 1
  fi
}

usage() {
  cat <<EOF

Usage:  $PROGRAM [OPTIONS] params

Options:

  -h,--help        show this help
  -j,--jenkins     jenkins folder
  -t,--tag         jenkins image tag

EOF
}


while (( "$#" )); do
  case "$1" in
    -h|-\?|--help) usage; exit 0 ;;
    -j|--jenkins) JENKINS="$2"; shift 2 ;;
    -t|--tag) TAG="$2"; shift 2 ;;
    -*|--*=) err "unsupported option $1"; exit 1 ;;
  esac
done

if ! has_docker; then
  exit 1
fi

if ! build "${JENKINS}" "${TAG}"; then
  exit 1
fi
