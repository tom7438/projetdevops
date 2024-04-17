
provider "google" {
  credentials = "${file("./mycredentials.json")}"
  project     = var.project
  region      = var.region
  zone        = var.zone
}



resource "google_compute_instance" "vm_instance" {

  ## for a setup having multiple instances of the same type, you can do
  ## the following, there would be 2 instances of the same configuration
  ## provisioned
  count        = 2
  name         = "${var.instance-name}-${count.index}"

  machine_type = "f1-micro"

  boot_disk {
    initialize_params {
      image = "debian-cloud/debian-10"
    }
  }

  network_interface {
    # A default network is created for all GCP projects
    network       = "default"
    access_config {
    }
  }

  # On ajoute notre clé publique dans la liste des hôtes acceptés sur la machine
  metadata = {
    # ======================================
    #
    # Ajoutez ici votre clé publique (cf : ./README) de la manière suivante
    # dev:<VOTRE_CLE> dev
    #
    # ======================================
    "ssh-keys" = <<EOT
      dev:ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQDEhvb/L4EgLndqkyuJ0Vcbfa9rt3UFpOJaDtn7KO5j+PlFzdP6/uki4TcWZRgVdFGc3q6S7NFk5rY1nR4RUhLzroloGDuTkLEWjZ9raHgVZRwUCXJN9u8ogwjcl5VUK7nRIhYC5hw7qleALNBWnH6ZfrafDdiOnnGNeCW34oSxYhLA6vTmQ7XXKr+wCRiAIUMBdHeFpsU+H7VrUtjDkMqzZJoEKhTDpubbr88Ms/uv+Z0V+FUHgbjKIjZsQoj3D3uZI59DSyzhqQeW2+ixrBZMFP6I6/BGrYMvwo1n0/D/7UqWKcJz8xBX215AgJ5djGDGtPdaULnmIz86txENlX5KWuTdxysma4GqYtWcxPOJepT/nPHAvceN95CdQLpcO5+TErO+WtbWPgi9O0+ZUwqJQZcYhD3efN16njEReEp7ONw9xAKooL2XYCCZ2ZPaJ4pCJwQWrb0L10o0MzEs+7IA43XBQ1FXUmHQMyC+PlijRoXDvmmQonLpigLcJ5DXFZkaNf5tZPSvP/WfwdaQFj1/jW5lYXHJ4PHl/Ia1flMii9Z3267rwec1oW/cDBE4hNhUEZoVG/bQBs0g9coslCafU2EiJ6mHHh01TOML1ESWHHEQAjpGvLjxYmQW1bPInUqvMfPJwd5hhHJWnBuFnbHmwLc4jpcA6OVjXFZKvzX1GQ== tom@tom-Lenovo-ideapad-530S-15IKB
      EOT
  }

  # On commence ensuite à executer du code sur la VM
  provisioner "remote-exec" {
    # On fait les mise à jours classiques et on installe python et autres outillages
    inline = [
      "sudo apt update",
      "sudo apt install -y software-properties-common"
    ]

    # On utilise la clé ssh pour se connecter, elle utilise la clé privée que vous avez en local dans ~/.shh/
    connection {
      type        = "ssh"
      # L'utilisateur dev est défini grâce a la clé ssh fourni dans les metadata
      user        = "dev"
      # Adresse IP de la machine
      host        = self.network_interface[0].access_config[0].nat_ip
    }
  }

  # Ensuite on exécute depuis un fichier local une commande pour modifier la VM
  # Pour cela, on utilise ansible et une connexion ssh avec la même clé privée que la dernière fois
  provisioner "local-exec" {
    command = "ansible-playbook -u dev -i ${self.network_interface.0.access_config.0.nat_ip}, ./ansible/main.yml"
  }
}

// A variable for extracting the external ip of the instance
output "ip" {
 value = "${google_compute_instance.vm_instance[0].network_interface.0.access_config.0.nat_ip}"
}
