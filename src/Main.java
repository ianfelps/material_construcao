import dao.*;
import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
            System.out.println("Login bem-sucedido. Bem-vindo ao sistema!");
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
            System.out.println("3. Gerenciar Vendas");
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
                case 3:
                    exibirMenuVenda(scanner);
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
        EnderecoClienteDAO enderecoDAO = new EnderecoClienteDAO();
        TelefoneClienteDAO telefoneDAO = new TelefoneClienteDAO();
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
                    listarClientes(clienteDAO, enderecoDAO, telefoneDAO);
                    break;
                case 2:
                    adicionarCliente(clienteDAO, enderecoDAO, telefoneDAO, scanner);
                    break;
                case 3:
                    deletarCliente(clienteDAO, enderecoDAO, telefoneDAO, scanner);
                    break;
                case 4:
                    atualizarCliente(clienteDAO, enderecoDAO, telefoneDAO, scanner);
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

    private static void exibirMenuVenda(Scanner scanner) {
        VendaDAO vendaDAO = new VendaDAO();
        ProdutoVendaDAO produtoVendaDAO = new ProdutoVendaDAO();
        int opcao;

        do {
            System.out.println("\n--- Menu de Vendas ---");
            System.out.println("1. Ver todas as vendas");
            System.out.println("2. Adicionar nova venda");
            System.out.println("3. Deletar venda");
            System.out.println("4. Atualizar venda");
            System.out.println("0. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha após o número

            switch (opcao) {
                case 1:
                    listarVendas(vendaDAO);
                    break;
                case 2:
                    adicionarVenda(vendaDAO, produtoVendaDAO, scanner);
                    break;
                case 3:
                    deletarVenda(vendaDAO, produtoVendaDAO, scanner);
                    break;
                case 4:
                    atualizarVenda(vendaDAO, produtoVendaDAO, scanner);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void listarClientes(ClienteDAO clienteDAO, EnderecoClienteDAO enderecoDAO, TelefoneClienteDAO telefoneDAO) {
        List<Cliente> clientes = clienteDAO.listarTodos();
        if (clientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados.");
        } else {
            System.out.println("Lista de Clientes:");
            for (Cliente cliente : clientes) {
                System.out.println(cliente.getIdCliente() + " - " + cliente.getNomeCliente());
                List<EnderecoCliente> enderecos = enderecoDAO.listarTodosPorCliente(cliente.getIdCliente());
                System.out.println("Endereços:");
                for (EnderecoCliente endereco : enderecos) {
                    System.out.println("  " + endereco.getNomeEndereco() + ", " + endereco.getNomeCidade() + " - " + endereco.getSiglaUF());
                }
                List<TelefoneCliente> telefones = telefoneDAO.listarTodosPorCliente(cliente.getIdCliente());
                System.out.println("Telefones:");
                for (TelefoneCliente telefone : telefones) {
                    System.out.println("  " + telefone.getNumeroTelefone());
                }
            }
        }
    }

    private static void adicionarCliente(ClienteDAO clienteDAO, EnderecoClienteDAO enderecoDAO, TelefoneClienteDAO telefoneDAO, Scanner scanner) {
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
        int idGerado = clienteDAO.inserir(novoCliente); // Inserir cliente e obter o ID

        if (idGerado > 0) {
            adicionarEndereco(enderecoDAO, scanner, idGerado);
            adicionarTelefone(telefoneDAO, scanner, idGerado);
            adicionarMaisEnderecosETelefones(enderecoDAO, telefoneDAO, scanner, idGerado);

            System.out.println("Cliente, Endereços e Telefones adicionados com sucesso!");
        } else {
            System.out.println("Erro ao adicionar cliente, endereços e telefones não foram adicionados.");
        }
    }

    private static void deletarCliente(ClienteDAO clienteDAO, EnderecoClienteDAO enderecoDAO, TelefoneClienteDAO telefoneDAO, Scanner scanner) {
        System.out.print("Digite o ID do cliente a ser deletado: ");
        int idCliente = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha após o número

        Cliente cliente = clienteDAO.buscarPorId(idCliente);
        if (cliente != null) {
            telefoneDAO.deletarPorIdCliente(idCliente); // Excluir telefones associados
            enderecoDAO.deletarPorIdCliente(idCliente); // Excluir endereços associados
            clienteDAO.deletar(idCliente); // Excluir o cliente por último
            System.out.println("Cliente, endereços e telefones deletados com sucesso!");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private static void atualizarCliente(ClienteDAO clienteDAO, EnderecoClienteDAO enderecoDAO, TelefoneClienteDAO telefoneDAO, Scanner scanner) {
        System.out.print("Digite o ID do cliente a ser atualizado: ");
        int idCliente = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha após o número

        Cliente cliente = clienteDAO.buscarPorId(idCliente);
        List<EnderecoCliente> enderecos = enderecoDAO.listarTodosPorCliente(idCliente);
        List<TelefoneCliente> telefones = telefoneDAO.listarTodosPorCliente(idCliente);

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

            if (!enderecos.isEmpty()) {
                for (EnderecoCliente endereco : enderecos) {
                    System.out.println("Endereço encontrado: " + endereco.getNomeEndereco());
                    System.out.print("Nova Sigla do Estado (UF): ");
                    String novaSiglaUF = scanner.nextLine();
                    System.out.print("Novo Nome do Endereço: ");
                    String novoNomeEndereco = scanner.nextLine();
                    System.out.print("Novo Nome da Cidade: ");
                    String novaCidade = scanner.nextLine();

                    endereco.setSiglaUF(novaSiglaUF);
                    endereco.setNomeEndereco(novoNomeEndereco);
                    endereco.setNomeCidade(novaCidade);
                    enderecoDAO.atualizar(endereco);
                }
                System.out.println("Endereço(s) atualizado(s) com sucesso!");
            } else {
                System.out.println("Endereço não encontrado para o cliente.");
            }

            if (!telefones.isEmpty()) {
                for (TelefoneCliente telefone : telefones) {
                    System.out.println("Telefone encontrado: " + telefone.getNumeroTelefone());
                    System.out.print("Novo número do Telefone: ");
                    String novoNumeroTelefone = scanner.nextLine();

                    telefone.setNumeroTelefone(novoNumeroTelefone);
                    telefoneDAO.atualizar(telefone);
                }
                System.out.println("Telefone(s) atualizado(s) com sucesso!");
            } else {
                System.out.println("Telefone não encontrado para o cliente.");
            }

            adicionarMaisEnderecosETelefones(enderecoDAO, telefoneDAO, scanner, idCliente);

            System.out.println("Cliente, Endereços e Telefones atualizados com sucesso!");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private static void adicionarEndereco(EnderecoClienteDAO enderecoDAO, Scanner scanner, int idCliente) {
        System.out.print("Sigla do Estado (UF): ");
        String siglaUF = scanner.nextLine();
        System.out.print("Nome do Endereço: ");
        String nomeEndereco = scanner.nextLine();
        System.out.print("Nome da Cidade: ");
        String nomeCidade = scanner.nextLine();

        EnderecoCliente novoEndereco = new EnderecoCliente(0, idCliente, siglaUF, nomeEndereco, nomeCidade);
        enderecoDAO.inserir(novoEndereco);

        System.out.println("Endereço adicionado com sucesso!");
    }

    private static void adicionarTelefone(TelefoneClienteDAO telefoneDAO, Scanner scanner, int idCliente) {
        System.out.print("Número do Telefone: ");
        String numeroTelefone = scanner.nextLine();

        TelefoneCliente novoTelefone = new TelefoneCliente(0, idCliente, numeroTelefone);
        telefoneDAO.inserir(novoTelefone);

        System.out.println("Telefone adicionado com sucesso!");
    }

    private static void adicionarMaisEnderecosETelefones(EnderecoClienteDAO enderecoDAO, TelefoneClienteDAO telefoneDAO, Scanner scanner, int idCliente) {
        String opcao;
        do {
            System.out.print("Deseja adicionar outro endereço para este cliente? (s/n): ");
            opcao = scanner.nextLine();
            if (opcao.equalsIgnoreCase("s")) {
                adicionarEndereco(enderecoDAO, scanner, idCliente);
            }
        } while (opcao.equalsIgnoreCase("s"));

        do {
            System.out.print("Deseja adicionar outro telefone para este cliente? (s/n): ");
            opcao = scanner.nextLine();
            if (opcao.equalsIgnoreCase("s")) {
                adicionarTelefone(telefoneDAO, scanner, idCliente);
            }
        } while (opcao.equalsIgnoreCase("s"));
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

        boolean sucesso = produtoDAO.inserir(novoProduto);
        if (sucesso) {
            System.out.println("Produto adicionado com sucesso!");
        } else {
            System.out.println("Erro ao adicionar produto. Verifique as informações e tente novamente.");
        }
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

    private static void listarVendas(VendaDAO vendaDAO) {
        List<Venda> vendas = vendaDAO.listarVendas();
        if (vendas.isEmpty()) {
            System.out.println("Não há vendas cadastradas.");
        } else {
            System.out.println("Lista de Vendas:");
            for (Venda venda : vendas) {
                System.out.println(venda.getIdVenda() + " - Cliente ID: " + venda.getIdCliente() +
                        " | Data/Hora Pagamento: " + venda.getDataHoraPagamento() +
                        " | Valor Total: R$ " + venda.getValorTotalVenda() +
                        " | Pago: " + (venda.isStatusPago() ? "Sim" : "Não"));
            }
        }
    }

    private static void adicionarVenda(VendaDAO vendaDAO, ProdutoVendaDAO produtoVendaDAO, Scanner scanner) {
        ClienteDAO clienteDAO = new ClienteDAO();

        System.out.print("ID do Cliente: ");
        int idCliente = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha após o número

        Cliente cliente = clienteDAO.buscarPorId(idCliente);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        Venda novaVenda = new Venda();
        novaVenda.setIdCliente(idCliente);

        // Permitir que o usuário insira a data da venda
        System.out.print("Data/Hora da venda (YYYY-MM-DDTHH:MM:SS) ou deixe vazio para nulo: ");
        String dataHoraVendaInput = scanner.nextLine();

        if (dataHoraVendaInput.isEmpty() && cliente.getTipoCliente().equalsIgnoreCase("Antigo")) {
            novaVenda.setDataHoraPagamento(null);
            novaVenda.setStatusPago(false);
        } else if (!dataHoraVendaInput.isEmpty()) {
            novaVenda.setDataHoraPagamento(LocalDateTime.parse(dataHoraVendaInput));
            novaVenda.setStatusPago(true);
        } else {
            // Condição onde data é nula para clientes não antigos
            System.out.println("Data de venda não pode ser nula para clientes do tipo não antigo.");
            return;
        }

        // Persistir a nova venda no banco de dados para obter seu ID
        vendaDAO.adicionarVenda(novaVenda);
        int idVenda = novaVenda.getIdVenda();
        novaVenda.setIdVenda(idVenda);

        // Lista para armazenar produtos adicionados
        List<ProdutoVenda> produtosAdicionados = new ArrayList<>();
        double valorTotalVenda = 0.0;

        // Adicionar produtos à venda
        String adicionarMaisProdutos;
        do {
            System.out.print("Código do Produto: ");
            int codigoProduto = scanner.nextInt();
            System.out.print("Quantidade do Produto Retirado: ");
            int quantidadeRetirada = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha após o número

            ProdutoVenda produtoVenda = new ProdutoVenda();
            produtoVenda.setIdVenda(idVenda); // Usar o ID da venda persistida
            produtoVenda.setCodigoProduto(codigoProduto);
            produtoVenda.setQuantidadeProdutoRetirado(quantidadeRetirada);

            try {
                produtoVendaDAO.adicionarProdutoVenda(produtoVenda);
                valorTotalVenda += produtoVenda.getValorTotalProduto();
                produtosAdicionados.add(produtoVenda);
                System.out.println("Produto adicionado à venda com sucesso!");
            } catch (RuntimeException e) {
                System.out.println("Erro ao adicionar produto à venda: " + e.getMessage());
            }

            System.out.print("Deseja adicionar mais produtos a esta venda? (s/n): ");
            adicionarMaisProdutos = scanner.nextLine();
        } while (adicionarMaisProdutos.equalsIgnoreCase("s"));

        // Mostrar detalhes da venda e pedir confirmação
        System.out.println("\n--- Detalhes da Venda ---");
        System.out.println("Cliente ID: " + idCliente);
        System.out.println("Data/Hora Pagamento: " + (novaVenda.getDataHoraPagamento() != null ? novaVenda.getDataHoraPagamento() : "N/A"));
        System.out.println("Produtos:");
        for (ProdutoVenda produto : produtosAdicionados) {
            System.out.println("  Código do Produto: " + produto.getCodigoProduto() + ", Quantidade: " + produto.getQuantidadeProdutoRetirado() + ", Valor Total: R$ " + produto.getValorTotalProduto());
        }
        System.out.println("Valor Total da Venda: R$ " + valorTotalVenda);

        // Confirmar venda
        System.out.print("\nConfirma essa venda? (s/n): ");
        String confirmacao = scanner.nextLine();
        if (!confirmacao.equalsIgnoreCase("s")) {
            // Se a venda não for confirmada, excluir os produtos adicionados e sair
            for (ProdutoVenda produto : produtosAdicionados) {
                produtoVendaDAO.excluirProdutoVenda(produto.getIdProdutoVenda());
            }
            // Excluir a venda também
            vendaDAO.excluirVenda(novaVenda.getIdVenda());
            System.out.println("Venda cancelada.");
            return;
        }

        // Persistir a venda com o valor total
        novaVenda.setValorTotalVenda(valorTotalVenda);
        vendaDAO.atualizarVenda(novaVenda);
        System.out.println("Venda adicionada com sucesso!");
    }

    private static void deletarVenda(VendaDAO vendaDAO, ProdutoVendaDAO produtoVendaDAO, Scanner scanner) {
        System.out.print("Digite o ID da venda a ser deletada: ");
        int idVenda = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha após o número

        Venda venda = vendaDAO.buscarVendaPorId(idVenda);  // Buscar detalhes da venda
        if (venda != null) {
            List<ProdutoVenda> produtosVenda = produtoVendaDAO.listarProdutosVenda();

            // Excluir produtos da venda primeiro
            for (ProdutoVenda produtoVenda : produtosVenda) {
                if (produtoVenda.getIdVenda() == idVenda) {
                    produtoVendaDAO.excluirProdutoVenda(produtoVenda.getIdProdutoVenda());
                }
            }

            // Em seguida, excluir a venda
            vendaDAO.excluirVenda(idVenda);
            System.out.println("Venda deletada com sucesso!");
        } else {
            System.out.println("Venda não encontrada.");
        }
    }

    private static void atualizarVenda(VendaDAO vendaDAO, ProdutoVendaDAO produtoVendaDAO, Scanner scanner) {
        System.out.print("Digite o ID da venda a ser atualizada: ");
        int idVenda = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha após o número

        Venda venda = vendaDAO.buscarVendaPorId(idVenda); // Buscar detalhes da venda
        if (venda != null) {
            System.out.println("Venda encontrada: ID Cliente - " + venda.getIdCliente());

            // Atualizar dados da venda
            System.out.print("Novo ID do Cliente: ");
            int novoIdCliente = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha após o número

            System.out.print("Novo status de pagamento (true para pago, false para não pago): ");
            boolean novoStatusPago = scanner.nextBoolean();
            scanner.nextLine(); // Consumir a nova linha após o boolean

            // Permitir que o usuário insira a nova data da venda
            System.out.print("Data/Hora da venda (YYYY-MM-DDTHH:MM:SS) ou deixe vazio para manter o valor atual: ");
            String novaDataHoraVendaInput = scanner.nextLine();

            ClienteDAO clienteDAO = new ClienteDAO();
            Cliente cliente = clienteDAO.buscarPorId(novoIdCliente); // Buscar tipo de cliente atualizado
            if (cliente == null) {
                System.out.println("Cliente não encontrado.");
                return;
            }

            if (novaDataHoraVendaInput.isEmpty() && venda.getDataHoraPagamento() == null && cliente.getTipoCliente().equalsIgnoreCase("Antigo")) {
                venda.setDataHoraPagamento(null);
            } else if (!novaDataHoraVendaInput.isEmpty()) {
                venda.setDataHoraPagamento(LocalDateTime.parse(novaDataHoraVendaInput));
            } else if (novaDataHoraVendaInput.isEmpty() && cliente.getTipoCliente().equalsIgnoreCase("Novo") && venda.getDataHoraPagamento() == null) {
                System.out.println("Data de venda não pode ser nula para clientes do tipo não antigo.");
                return;
            }

            // Atualiza os novos dados da venda
            venda.setIdCliente(novoIdCliente);
            venda.setStatusPago(novoStatusPago);

            vendaDAO.atualizarVenda(venda);
            System.out.println("Venda atualizada com sucesso!");

            // Perguntar se deseja atualizar produtos da venda
            System.out.print("Deseja atualizar os produtos dessa venda? (s/n): ");
            String atualizarProdutos = scanner.nextLine();
            if (atualizarProdutos.equalsIgnoreCase("s")) {
                System.out.print("Deseja limpar os produtos existentes e adicionar novos? (s/n): ");
                String limparProdutos = scanner.nextLine();
                if (limparProdutos.equalsIgnoreCase("s")) {
                    // Excluir produtos existentes da venda
                    List<ProdutoVenda> produtosVenda = produtoVendaDAO.listarProdutosVenda();
                    for (ProdutoVenda produtoVenda : produtosVenda) {
                        if (produtoVenda.getIdVenda() == idVenda) {
                            produtoVendaDAO.excluirProdutoVenda(produtoVenda.getIdProdutoVenda());
                        }
                    }
                }

                // Adicionar novos produtos
                double novoValorTotalVenda = 0.0;
                String adicionarMaisProdutos;
                do {
                    System.out.print("Código do Produto: ");
                    int codigoProduto = scanner.nextInt();
                    System.out.print("Quantidade do Produto Retirado: ");
                    int quantidadeRetirada = scanner.nextInt();
                    scanner.nextLine(); // Consumir a nova linha após o número

                    ProdutoVenda produtoVenda = new ProdutoVenda();
                    produtoVenda.setIdVenda(venda.getIdVenda());
                    produtoVenda.setCodigoProduto(codigoProduto);
                    produtoVenda.setQuantidadeProdutoRetirado(quantidadeRetirada);

                    try {
                        produtoVendaDAO.adicionarProdutoVenda(produtoVenda);
                        novoValorTotalVenda += produtoVenda.getValorTotalProduto();
                        System.out.println("Produto adicionado à venda com sucesso!");
                    } catch (RuntimeException e) {
                        System.out.println("Erro ao adicionar produto à venda: " + e.getMessage());
                    }

                    System.out.print("Deseja adicionar mais produtos a esta venda? (s/n): ");
                    adicionarMaisProdutos = scanner.nextLine();
                } while (adicionarMaisProdutos.equalsIgnoreCase("s"));

                venda.setValorTotalVenda(novoValorTotalVenda);
                vendaDAO.atualizarVenda(venda);
                System.out.println("Produtos da venda atualizados com sucesso!");
            }
        } else {
            System.out.println("Venda não encontrada.");
        }
    }
}