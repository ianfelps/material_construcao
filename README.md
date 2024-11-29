# Sistema para gestão de Material de Construção
O projeto é um sistema de gerenciamento voltado para a administração de um comércio de materiais de construção, que abrange funcionalidades para o controle de clientes, produtos e vendas. Ele foi desenvolvido para facilitar a gestão de informações e operações em um ambiente comercial, proporcionando uma interface amigável e eficiente para os usuários, que geralmente são administradores ou gerentes da loja.

# Tecnologias Utilizadas
- **Back-end:** Java (Linguagem do sistema);
- **Front-end:** Swing (Interface gráfica);
- **Banco de Dados:** MySQL;
- **Ferramentas de Desenvolvimento:** IDE (IntelliJ), Git para controle de versão.

# Diagramas

### Diagrama de Classe
  ![Diagrama de Classe](/imgs/diagrama_classe.png "Diagrama de Classe")  
  [Link para o PDF](/imgs/diagrama_classe.pdf)

### Diagrama de Caso de Uso
  ![Diagrama de Caso de Uso](/imgs/diagrama_casodeuso.png "Diagrama de Caso de Uso")

# Modelagem do Banco de Dados

### Modelo Lógico
  ![Modelo Lógico](/imgs/modelo_logico.png "Modelo Lógico")

### Modelo Conceitual
  ![Modelo Conceitual](/imgs/modelo_conceitual.png "Modelo Conceitual")

# Estrutura do Banco de Dados

### TB_CLIENTE
- Armazena informações sobre os clientes.
- Campos incluem: ID_CLIENTE (chave primária), nome do cliente (NO_CLIENTE), RG (NR_RG), CPF (NR_CPF) e tipo de cliente (TP_CLIENTE).
- Relacionamentos:
  - Relaciona-se com TB_TELEFONE_CLIENTE (1:N) e TB_ENDERECO_CLIENTE (1:N) para armazenar informações adicionais sobre a loja.
  - Relaciona-se com TB_VENDA (1:N), indicando as vendas realizadas por cada cliente.

### TB_ENDERECO_CLIENTE
- Contém os endereços associados aos clientes.
- Campos incluem: ID_ENDERECO_CLIENTE (chave primária), ID_CLIENTE (chave estrangeira), estado (SG_UF), endereço (NO_ENDERECO) e cidade (NO_CIDADE).

### TB_TELEFONE_CLIENTE
- Armazena os telefones dos clientes.
- Campos incluem: ID_TELEFONE_CLIENTE (chave primária), ID_CLIENTE (chave estrangeira) e número de telefone (NR_TELEFONE).

### TB_VENDA
- Registra as vendas realizadas.
- Campos incluem: ID_VENDA (chave primária), ID_CLIENTE (chave estrangeira), data/hora do pagamento (DH_PAGAMENTO), valor total da venda (VL_TOTAL_VENDA) e indicador de pagamento (IC_PAGO).
- Relaciona-se com TB_PRODUTO_VENDA (1:N), indicando os produtos vendidos.

### TB_PRODUTO_VENDA
- Associa produtos às vendas.
- Campos incluem: ID_PRODUTO_VENDA (chave primária), ID_VENDA (chave estrangeira), código do produto (CD_PRODUTO), quantidade retirada (QT_PRODUTO_RETIRADO) e valor total do produto (VL_TOTAL_PRODUTO).
- Relaciona-se com TB_PRODUTO para detalhar os produtos.

### TB_PRODUTO
- Armazena informações sobre os produtos.
- Campos incluem: código do produto (CD_PRODUTO, chave primária), ID_LOJA (chave estrangeira), nome do produto (NO_PRODUTO), quantidade em estoque (QT_PRODUTO_ESTOQUE) e valor unitário (VL_PRODUTO_UNITARIO).

### TB_LOJA
- Contém informações sobre as lojas.
- Campos incluem: ID_LOJA (chave primária), nome da loja (NO_LOJA) e CNPJ (NR_CNPJ).
- Relaciona-se com TB_TELEFONE_LOJA (1:N) e TB_ENDERECO_LOJA (1:N) para armazenar informações adicionais sobre a loja.

### TB_TELEFONE_LOJA
- Armazena os telefones das lojas.
- Campos incluem: ID_TELEFONE_LOJA (chave primária), ID_LOJA (chave estrangeira) e número de telefone (NR_TELEFONE).

### TB_ENDERECO_LOJA
- Contém os endereços das lojas.
- Campos incluem: ID_ENDERECO_LOJA (chave primária), ID_LOJA (chave estrangeira), estado (SG_UF), endereço (NO_ENDERECO) e cidade (NO_CIDADE).

### TB_ADMINISTRADOR
- Registra informações de login dos administradores do sistema.
- Campos incluem: nome de login (NO_LOGIN) e senha (NO_SENHA).

# Estrutura do Código

### Pacote 'dao'
- Contém as classes de acesso a dados (Data Access Object) que gerenciam a interação com o banco de dados para as entidades correspondentes.
  - ClienteDAO: Gerencia operações de banco de dados relacionadas a clientes (inserir, atualizar, deletar, buscar).
  - ProdutoDAO: Gerencia operações de banco de dados relacionadas a produtos.
  - VendaDAO: Gerencia operações de banco de dados relacionadas a vendas.
  - EnderecoClienteDAO: Gerencia operações de banco de dados relacionadas a endereços de clientes.
  - ProdutoVendaDAO: Gerencia operações de banco de dados relacionadas a produtos vendidos.

### Pacote 'database'
- Contém a classe para conexão com o Banco de Dados.
  - ConnectionFactory: Gerencia a conexão com o banco de dados MySQL.

### Pacote 'model'
- Contém as classes que representam as entidades do sistema.
  - Cliente: Representa um cliente com atributos como ID, nome, RG, CPF e tipo.
  - Produto: Representa um produto com atributos como ID, nome, quantidade e valor unitário.
  - Venda: Representa uma venda com atributos como ID, ID do cliente, data de pagamento, valor total e status de pagamento.
  - EnderecoCliente: Representa o endereço de um cliente.
  - TelefoneCliente: Representa o telefone de um cliente.
  - ProdutoVenda: Representa a relação entre produtos e vendas.
  - Administrador: Representa um administrador do sistema.

### Pacote 'view'
- Contém as classes que gerenciam a interface gráfica do usuário (GUI).
  - TelaCliente: Interface para gerenciar clientes, com botões para adicionar, atualizar e deletar clientes.
  - ClienteDialog: Diálogo para adicionar ou editar informações de um cliente, incluindo telefones e endereços.
  - TelaProduto: Interface para gerenciar produtos, com funcionalidades semelhantes à TelaCliente.
  - ProdutoDialog: Diálogo para adicionar ou editar informações de um produto.
  - TelaVenda: Interface para registrar vendas, permitindo a seleção de clientes e produtos.
  - TelaRelatorio: Interface para visualizar e gerenciar relatórios de vendas.
  - TelaMenu: Menu principal do sistema, permitindo navegação entre diferentes telas.

# Interface Gráfica

### Tela de Login
  ![Tela de Login](/imgs/tela_login.png "Tela de Login")

### Tela de Menu Principal
  ![Tela de Menu Principal](/imgs/tela_menu.png "Tela de Menu Principal")

### Tela de Gerenciamento de Clientes
  ![Tela de Gerenciamento de Clientes](/imgs/tela_cliente.png "Tela de Gerenciamento de Clientes")

### Tela de Gerenciamento de Produtos
  ![Tela de Gerenciamento de Produtos](/imgs/tela_produto.png "Tela de Gerenciamento de Produtos")

### Tela de Registrar Venda
  ![Tela de Registrar Venda](/imgs/tela_venda.png "Tela de Registrar Venda")

### Tela de Relatórios
  ![Tela de Relatórios](/imgs/tela_relatorio.png "Tela de Relatórios")

# Fluxo de Sistema

### 1. Início do Sistema
- O sistema inicia com a TelaLogin, onde o usuário (administrador) insere suas credenciais (usuário e senha). O AdministradorDAO é utilizado para validar as credenciais.
  - Se as credenciais forem válidas, o usuário é direcionado para a TelaMenu.
  - Se as credenciais forem inválidas, uma mensagem de erro é exibida e o usuário pode tentar novamente.

### 2. Menu Principal
- Após o login bem-sucedido, a TelaMenu é exibida. Esta tela oferece opções para:
  - Gerenciar Clientes (navegar para TelaCliente).
  - Gerenciar Produtos (navegar para TelaProduto).
  - Registrar Venda (navegar para TelaVenda).
  - Gerenciar Relatórios (navegar para TelaRelatorio).
  - Sair do Sistema.

### 3. Gerenciamento de Clientes
- Ao selecionar "Gerenciar Clientes", a TelaCliente é exibida.
- O usuário pode adicionar, atualizar ou deletar clientes.
- A lista de clientes é carregada utilizando o ClienteDAO, que busca todos os clientes do banco de dados.
- Para adicionar ou atualizar um cliente, um ClienteDialog é aberto, permitindo a entrada de dados do cliente, incluindo telefones e endereços.
- As alterações são salvas no banco de dados através do ClienteDAO.

### 4. Gerenciamento de Produtos
- Ao selecionar "Gerenciar Produtos", a TelaProduto é exibida.
- O usuário pode adicionar, atualizar ou deletar produtos.
- A lista de produtos é carregada utilizando o ProdutoDAO.
- Para adicionar ou atualizar um produto, um ProdutoDialog é aberto, permitindo a entrada de dados do produto.
- As alterações são salvas no banco de dados através do ProdutoDAO.

### 5. Registro de Vendas
- Ao selecionar "Registrar Venda", a TelaVenda é exibida.
- O usuário seleciona um cliente a partir de um JComboBox que é preenchido com os clientes do banco de dados.
- O usuário pode adicionar produtos à venda, especificando a quantidade. A tabela de produtos é gerenciada por um DefaultTableModel.
- O total da venda é calculado e, ao confirmar, a venda é registrada no banco de dados através do VendaDAO, e os produtos são registrados na venda através do ProdutoVendaDAO.
- O sistema verifica se o cliente é novo e se a data de pagamento é necessária.

### 6. Gerenciamento de Relatórios
- Ao selecionar "Gerenciar Relatórios", a TelaRelatorio é exibida.
- A lista de vendas é carregada utilizando o VendaDAO.
- O usuário pode visualizar detalhes de uma venda selecionada ou deletar uma venda.
- Ao visualizar uma venda, um painel de detalhes é exibido, mostrando informações sobre a venda, cliente, endereço e produtos vendidos.

### 7. Encerramento do Sistema
- O usuário pode optar por sair do sistema a qualquer momento através do botão "Sair" na TelaMenu, que fecha a aplicação.

# Funcionalidades

### Funcionalidades de Login
- Login de Administrador: Permite que o administrador insira suas credenciais (usuário e senha) para acessar o sistema.
- Validação de Credenciais: Verifica se as credenciais fornecidas são válidas.

### Funcionalidades do Menu Principal
- Navegação: Permite ao usuário navegar para diferentes seções do sistema:
  - Gerenciar Clientes.
  - Gerenciar Produtos.
  - Registrar Venda.
  - Gerenciar Relatórios.
  - Sair do Sistema.

### Funcionalidades de Gerenciamento de Clientes
- Listar Clientes: Exibe uma tabela com todos os clientes cadastrados.
- Adicionar Cliente: Permite ao usuário inserir novos clientes, incluindo informações como nome, RG, CPF e tipo.
- Atualizar Cliente: Permite ao usuário editar as informações de um cliente existente.
- Deletar Cliente: Permite ao usuário remover um cliente do sistema.
- Gerenciar Telefones: Adicionar e remover telefones associados ao cliente.
- Gerenciar Endereços: Adicionar e remover endereços associados ao cliente.

### Funcionalidades de Gerenciamento de Produtos
- Listar Produtos: Exibe uma tabela com todos os produtos cadastrados.
- Adicionar Produto: Permite ao usuário inserir novos produtos, incluindo informações como nome, quantidade e valor unitário.
- Atualizar Produto: Permite ao usuário editar as informações de um produto existente.
- Deletar Produto: Permite ao usuário remover um produto do sistema.

### Funcionalidades de Registro de Vendas
- Selecionar Cliente: Permite ao usuário escolher um cliente para a venda.
- Adicionar Produtos à Venda: Permite ao usuário adicionar produtos à venda, especificando a quantidade.
- Remover Produtos da Venda: Permite ao usuário remover produtos da tabela de venda.
- Calcular Valor Total da Venda: Calcula automaticamente o valor total da venda com base nos produtos e quantidades selecionadas.
- Registrar Venda: Salva a venda no banco de dados, incluindo informações sobre o cliente e produtos vendidos.

### Funcionalidades de Gerenciamento de Relatórios
- Listar Vendas: Exibe uma tabela com todas as vendas registradas.
- Visualizar Detalhes da Venda: Permite ao usuário visualizar informações detalhadas sobre uma venda específica, incluindo cliente, produtos e valores.
- Deletar Venda: Permite ao usuário remover uma venda do sistema.

### Funcionalidades Gerais
- Validação de Dados: Verifica se os dados inseridos pelo usuário são válidos (por exemplo, se os campos obrigatórios estão preenchidos).
- Mensagens de Confirmação: Exibe mensagens de confirmação para ações críticas, como deletar clientes ou vendas.
- Feedback ao Usuário: Fornece mensagens de sucesso ou erro durante a execução de operações.

### Funcionalidades de Interface Gráfica
- Interface Intuitiva: Utiliza componentes Swing para criar uma interface amigável e fácil de usar.
- Diálogos de Entrada: Utiliza diálogos para entrada de dados, como para adicionar ou editar clientes e produtos.

### Sistema para gestão de Materiais de Construção feito em Java integrado com Banco de Dados. Projeto final da disciplina de POO na UCB.
