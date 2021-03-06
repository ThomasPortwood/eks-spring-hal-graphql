resource "kubernetes_deployment" "graphql" {
  metadata {
    name = "graphql"
    labels = {
      App = "graphql"
    }
    namespace = kubernetes_namespace.graphql.metadata[0].name
  }

  spec {
    replicas = 1
    selector {
      match_labels = {
        App = "graphql"
      }
    }
    template {
      metadata {
        labels = {
          App = "graphql"
        }
      }

      spec {

        image_pull_secrets {
          name = "regcred"
        }

        container {
          image = "${var.github_docker_registry_url}:${var.docker_image_tag}"
          name  = "graphql"

          port {
            container_port = 8080
          }

          env {
            name  = "SPRING_DATASOURCE_URL"
            value = var.spring_datasource_url
          }

          env {
            name  = "SPRING_DATASOURCE_USERNAME"
            value = var.spring_datasource_username
          }

          env {
            name  = "SPRING_DATASOURCE_PASSWORD"
            value = var.spring_datasource_password
          }

          resources {
            limits {
              cpu    = "2.0"
              memory = "4096Mi"
            }
            requests {
              cpu    = "250m"
              memory = "50Mi"
            }
          }
        }
      }
    }
  }
}