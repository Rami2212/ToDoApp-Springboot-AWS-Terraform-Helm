#################################
# VPC
#################################
module "vpc" {
  source  = "terraform-aws-modules/vpc/aws"
  version = "~> 5.0"

  name = "${var.cluster_name}-vpc"
  cidr = var.vpc_cidr

  azs             = ["${var.aws_region}a", "${var.aws_region}b"]
  public_subnets  = var.public_subnets
  private_subnets = var.private_subnets

  enable_nat_gateway = true
  single_nat_gateway = true

  tags = {
    Project = "todoapp"
  }
}

#################################
# EKS Cluster
#################################
module "eks" {
  source  = "terraform-aws-modules/eks/aws"
  version = "~> 20.0"

  cluster_name    = var.cluster_name
  cluster_version = "1.30"

  # Attach to VPC we just created
  vpc_id     = module.vpc.vpc_id
  subnet_ids = module.vpc.private_subnets

  # Where to put control plane logs (CloudWatch)
  enable_irsa = true

  cluster_endpoint_public_access = true

  # Managed node group
  eks_managed_node_groups = {
    default = {
      desired_size = var.desired_capacity
      min_size     = var.min_capacity
      max_size     = var.max_capacity

      instance_types = var.node_instance_types

      capacity_type = "ON_DEMAND"

      tags = {
        Name    = "${var.cluster_name}-node"
        Project = "todoapp"
      }
    }
  }

  tags = {
    Project = "todoapp"
  }
}
