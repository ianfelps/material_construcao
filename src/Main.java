import dao.AdministradorDAO;
import dao.ClienteDAO;
import dao.ProdutoDAO;
import model.Cliente;
import model.Produto;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AdministradorDAO administradorDAO = new AdministradorDAO();

        System.out.println("--- Sistema de Login ---");
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        if (administradorDAO.validarLogin(login, senha)) {
            System.out.println("Login bem-sucedido. Bem-vindo ao sistema de clientes!");
            exibirMenuPrincipal(scanner);
        } else {
            System.out.println("Login ou senha inválidos. Encerrando o sistema.");
            System.exit(0); // Encerra o programa
        }
    }

    private static void exibirMenuPrincipal(Scanner scanner) {
        int opcao;

        do {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1. Gerenciar Clientes");
            System.out.println("2. Gerenciar Produtos");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha após o número

            switch (opcao) {
                case 1:
                    exibirMenuCliente(scanner);
                    break;
                case 2:
                    exibirMenuProduto(scanner);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void exibirMenuCliente(Scanner scanner) {
        ClienteDAO clienteDAO = new ClienteDAO();
        int opcao;

        do {
            System.out.println("\n--- Menu de Clientes ---");
            System.out.println("1. Ver todos os clientes");
            System.out.println("2. Adicionar novo cliente");
            System.out.println("3. Deletar cliente");
            System.out.println("4. Atualizar cliente");
            System.out.println("0. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha após o número

            switch (opcao) {
                case 1:
                    listarClientes(clienteDAO);
                    break;
                case 2:
                    adicionarCliente(clienteDAO, scanner);
                    break;
                case 3:
                    deletarCliente(clienteDAO, scanner);
                    break;
                case 4:
                    atualizarCliente(clienteDAO, scanner);
                    break;
                case 0:
                    System.out.println("Encerrando o sistema. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void exibirMenuProduto(Scanner scanner) {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        int opcao;

        do {
            System.out.println("\n--- Menu de Produtos ---");
            System.out.println("1. Ver todos os produtos");
            System.out.println("2. Adicionar novo produto");
            System.out.println("3. Deletar produto");
            System.out.println("4. Atualizar produto");
            System.out.println("0. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha após o número

            switch (opcao) {
                case 1:
                    listarProdutos(produtoDAO);
                    break;
                case 2:
                    adicionarProduto(produtoDAO, scanner);
                    break;
                case 3:
                    deletarProduto(produtoDAO, scanner);
                    break;
                case 4:
                    atualizarProduto(produtoDAO, scanner);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void listarClientes(ClienteDAO clienteDAO) {
        List<Cliente> clientes = clienteDAO.listarTodos();
        if (clientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados.");
        } else {
            System.out.println("Lista de Clientes:");
            for (Cliente cliente : clientes) {
                System.out.println(cliente.getIdCliente() + " - " + cliente.getNomeCliente());
            }
        }
    }

    private static void adicionarCliente(ClienteDAO clienteDAO, Scanner scanner) {
        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Número do RG: ");
        int rg = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha após o número
        System.out.print("Número do CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Tipo de cliente (Novo ou Antigo): ");
        String tipoCliente = scanner.nextLine();

        Cliente novoCliente = new Cliente(0, nome, rg, cpf, tipoCliente);
//        novoCliente.setNomeCliente(nome);
//        novoCliente.setRg(rg);
//        novoCliente.setCpf(cpf);
//        novoCliente.setTipoCliente(tipoCliente);

        clienteDAO.inserir(novoCliente);
        System.out.println("Cliente adicionado com sucesso!");
    }

    private static void deletarCliente(ClienteDAO clienteDAO, Scanner scanner) {
        System.out.print("Digite o ID do cliente a ser deletado: ");
        int idCliente = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha após o número

        Cliente cliente = clienteDAO.buscarPorId(idCliente);
        if (cliente != null) {
            clienteDAO.deletar(idCliente);
            System.out.println("Cliente deletado com sucesso!");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private static void atualizarCliente(ClienteDAO clienteDAO, Scanner scanner) {
        System.out.print("Digite o ID do cliente a ser atualizado: ");
        int idCliente = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha após o número

        Cliente cliente = clienteDAO.buscarPorId(idCliente);
        if (cliente != null) {
            System.out.println("Cliente encontrado: " + cliente.getNomeCliente());
            System.out.print("Novo nome: ");
            String novoNome = scanner.nextLine();
            System.out.print("Novo número do RG: ");
            int novoRg = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha após o número
            System.out.print("Novo número do CPF: ");
            String novoCpf = scanner.nextLine();
            System.out.print("Novo tipo de cliente (Novo ou Antigo): ");
            String novoTipoCliente = scanner.nextLine();

            cliente.setNomeCliente(novoNome);
            cliente.setRg(novoRg);
            cliente.setCpf(novoCpf);
            cliente.setTipoCliente(novoTipoCliente);

            clienteDAO.atualizar(cliente);
            System.out.println("Cliente atualizado com sucesso!");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private static void listarProdutos(ProdutoDAO produtoDAO) {
        List<Produto> produtos = produtoDAO.listarTodos();
        if (produtos.isEmpty()) {
            System.out.println("Não há produtos cadastrados.");
        } else {
            System.out.println("Lista de Produtos:");
            for (Produto produto : produtos) {
                System.out.println(produto.getIdProduto() + " - " + produto.getNomeProduto() +
                        " | Quantidade: " + produto.getQuantidadeProduto() +
                        " | Valor Unitário: R$ " + produto.getValorUnitario());
            }
        }
    }

    private static void adicionarProduto(ProdutoDAO produtoDAO, Scanner scanner) {
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("ID da loja: ");
        int idLoja = scanner.nextInt();
        System.out.print("Quantidade do produto: ");
        int quantidade = scanner.nextInt();
        System.out.print("Valor unitário: ");
        double valorUnitario = scanner.nextDouble();
        scanner.nextLine(); // Consumir a nova linha após o número

        Produto novoProduto = new Produto(0, idLoja, nome, quantidade, valorUnitario);
//        novoProduto.setNomeProduto(nome);
//        novoProduto.setIdLoja(idLoja);
//        novoProduto.setQuantidadeProduto(quantidade);
//        novoProduto.setValorUnitario(valorUnitario);

        produtoDAO.inserir(novoProduto);
        System.out.println("Produto adicionado com sucesso!");
    }

    private static void deletarProduto(ProdutoDAO produtoDAO, Scanner scanner) {
        System.out.print("Digite o ID do produto a ser deletado: ");
        int idProduto = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha após o número

        Produto produto = produtoDAO.buscarPorId(idProduto);
        if (produto != null) {
            produtoDAO.deletar(idProduto);
            System.out.println("Produto deletado com sucesso!");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private static void atualizarProduto(ProdutoDAO produtoDAO, Scanner scanner) {
        System.out.print("Digite o ID do produto a ser atualizado: ");
        int idProduto = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha após o número

        Produto produto = produtoDAO.buscarPorId(idProduto);
        if (produto != null) {
            System.out.println("Produto encontrado: " + produto.getNomeProduto());
            System.out.print("Novo nome do produto: ");
            String novoNome = scanner.nextLine();
            System.out.print("Nova quantidade do produto: ");
            int novaQuantidade = scanner.nextInt();
            System.out.print("Novo valor unitário: ");
            double novoValorUnitario = scanner.nextDouble();
            scanner.nextLine(); // Consumir a nova linha após o número

            produto.setNomeProduto(novoNome);
            produto.setQuantidadeProduto(novaQuantidade);
            produto.setValorUnitario(novoValorUnitario);

            produtoDAO.atualizar(produto);
            System.out.println("Produto atualizado com sucesso!");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }
}