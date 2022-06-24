variables {
  REPOSITORY = "gabsss/toll-repository"
  USERNAME   = "gabsss"
  PASSWORD   = "Caminhoilegra21!"
}

source "docker" "toll" {
  changes = [
    "EXPOSE 8000",
    "ENTRYPOINT [\"java -jar /var/jenkins_home/workspace/Get_Toll_Artifact/org.gabrielyget/Toll/1.0/Toll-1.0.jar\"]"
  ]
  commit = "true"
  image  = "ubuntu:18.04"
  pull   = "true"
}

build {
  sources = ["source.docker.toll"]

  provisioner "shell" {
    script = "install-ansible.sh"
  }

  provisioner "ansible-local" {
    playbook_file = "common.yml"
  }

  post-processors {
    post-processor "docker-tag" {
      repository = "${var.REPOSITORY}"
      tags       = ["latest"]
    }

    post-processor "docker-push" {
      login          = true
      login_username = "${var.USERNAME}"
      login_password = "${var.PASSWORD}"
    }
  }
}

