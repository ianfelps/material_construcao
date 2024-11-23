import dao.ClienteDAO;
import model.Cliente;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClienteDAO clienteDAO = new ClienteDAO();
        int opcao;

        do {
            System.out.println("\n--- Menu de Clientes ---");
            System.out.println("1. Ver todos os clientes");
            System.out.println("2. Adicionar novo cliente");
            System.out.println("3. Deletar cliente");
            System.out.println("4. Atualizar cliente");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha após o número

            switch (opcao) {
                case 1:
                    // Ver todos os clientes
                    listarClientes(clienteDAO);
                    break;
                case 2:
                    // Adicionar um novo cliente
                    adicionarCliente(clienteDAO, scanner);
                    break;
                case 3:
                    // Deletar um cliente
                    deletarCliente(clienteDAO, scanner);
                    break;
                case 4:
                    // Atualizar um cliente
                    atualizarCliente(clienteDAO, scanner);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    // metodo para listar todos os clientes
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

    // metodo para adicionar um novo cliente
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

        Cliente novoCliente = new Cliente();
        novoCliente.setNomeCliente(nome);
        novoCliente.setRg(rg);
        novoCliente.setCpf(cpf);
        novoCliente.setTipoCliente(tipoCliente);

        clienteDAO.inserir(novoCliente);
        System.out.println("Cliente adicionado com sucesso!");
    }

    // metodo para deletar um cliente
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

    // metodo para atualizar um cliente
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
}