
import java.util.Scanner;

class Medicamento {
    int codigo;
    String nome;
    double preco;
    int quantidade;
    int validade;
}

public class FarmaControl {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Medicamento[] estoque = new Medicamento[50];

        int cadastrados = 0;
        int vendas = 0;
        int unidadesVendidas = 0;
        double faturamento = 0;
        int opcao;

        System.out.println("=== FARMACONTROL ===");
        System.out.print("Informe o mes atual (MMAAAA): ");
        int dataAtual = scanner.nextInt();

        int mesAtual = dataAtual / 10000;
        int anoAtual = dataAtual % 10000;

        if (mesAtual < 1 || mesAtual > 12 ||
                anoAtual < 2000 || anoAtual > 2099) {
            System.out.println("Data atual invalida!");
            scanner.close();
            return;
        }

        int referencia = anoAtual * 100 + mesAtual;

        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1 - Cadastrar medicamento");
            System.out.println("2 - Consultar medicamento");
            System.out.println("3 - Listar estoque");
            System.out.println("4 - Registrar venda");
            System.out.println("5 - Verificar alertas");
            System.out.println("6 - Relatorio geral");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");

            opcao = scanner.nextInt();

            switch (opcao) {

                case 1:
                    System.out.println("\n--- CADASTRO ---");

                    if (cadastrados == 50) {
                        System.out.println("Estoque cheio!");
                        break;
                    }

                    System.out.print("Codigo: ");
                    int codigo = scanner.nextInt();

                    if (codigo <= 0) {
                        System.out.println("Codigo invalido!");
                        break;
                    }

                    int i = 0;

                    while (i < cadastrados &&
                            estoque[i].codigo != codigo) {
                        i++;
                    }

                    if (i < cadastrados) {
                        System.out.println("Codigo ja cadastrado!");
                        break;
                    }

                    scanner.nextLine();

                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();

                    if (nome.length() == 0) {
                        System.out.println("Nome invalido!");
                        break;
                    }

                    System.out.print("Preco: ");
                    double preco = scanner.nextDouble();

                    System.out.print("Quantidade: ");
                    int quantidade = scanner.nextInt();

                    System.out.print("Validade (MMAAAA): ");
                    int dataValidade = scanner.nextInt();

                    int mesValidade = dataValidade / 10000;
                    int anoValidade = dataValidade % 10000;

                    if (preco <= 0 || quantidade < 0) {
                        System.out.println("Preco ou quantidade invalida!");
                        break;
                    }

                    if (mesValidade < 1 || mesValidade > 12 ||
                            anoValidade < 2000 || anoValidade > 2099) {
                        System.out.println("Validade invalida!");
                        break;
                    }

                    Medicamento novo = new Medicamento();

                    novo.codigo = codigo;
                    novo.nome = nome;
                    novo.preco = preco;
                    novo.quantidade = quantidade;
                    novo.validade = anoValidade * 100 + mesValidade;

                    estoque[cadastrados] = novo;
                    cadastrados++;

                    System.out.println("Medicamento cadastrado!");
                    break;

                case 2:
                    System.out.println("\n--- CONSULTA ---");

                    System.out.print("Codigo: ");
                    int busca = scanner.nextInt();

                    int posicao = 0;

                    while (posicao < cadastrados &&
                            estoque[posicao].codigo != busca) {
                        posicao++;
                    }

                    if (posicao == cadastrados) {
                        System.out.println("Medicamento nao encontrado!");
                    } else {
                        Medicamento m = estoque[posicao];

                        System.out.println("Codigo: " + m.codigo);
                        System.out.println("Nome: " + m.nome);
                        System.out.printf("Preco: R$ %.2f%n", m.preco);
                        System.out.println("Quantidade: " + m.quantidade);
                        System.out.printf("Validade: %02d%04d%n",
                                m.validade % 100, m.validade / 100);

                        if (m.validade < referencia) {
                            System.out.println("Status: VENCIDO");
                        } else if (m.quantidade == 0) {
                            System.out.println("Status: SEM ESTOQUE");
                        } else {
                            System.out.println("Status: DISPONIVEL");
                        }
                    }
                    break;

                case 3:
                    System.out.println("\n--- ESTOQUE ---");

                    if (cadastrados == 0) {
                        System.out.println("Estoque vazio!");
                    }

                    for (int j = 0; j < cadastrados; j++) {
                        Medicamento m = estoque[j];

                        System.out.println("--------------------");
                        System.out.println("Codigo: " + m.codigo);
                        System.out.println("Nome: " + m.nome);
                        System.out.printf("Preco: R$ %.2f%n", m.preco);
                        System.out.println("Quantidade: " + m.quantidade);
                        System.out.printf("Validade: %02d%04d%n",
                                m.validade % 100, m.validade / 100);
                    }
                    break;

                case 4:
                    System.out.println("\n--- VENDA ---");

                    System.out.print("Codigo: ");
                    int codigoVenda = scanner.nextInt();

                    int indice = 0;

                    while (indice < cadastrados &&
                            estoque[indice].codigo != codigoVenda) {
                        indice++;
                    }

                    if (indice == cadastrados) {
                        System.out.println("Medicamento nao encontrado!");
                        break;
                    }

                    Medicamento produto = estoque[indice];

                    if (produto.validade < referencia) {
                        System.out.println(
                                "Medicamento vencido! Venda bloqueada."
                        );
                        break;
                    }

                    System.out.println("Produto: " + produto.nome);
                    System.out.println("Disponivel: " + produto.quantidade);

                    System.out.print("Quantidade da venda: ");
                    int quantidadeVenda = scanner.nextInt();

                    if (quantidadeVenda <= 0) {
                        System.out.println("Quantidade invalida!");
                    } else if (quantidadeVenda > produto.quantidade) {
                        System.out.println("Estoque insuficiente!");
                    } else {
                        double total = produto.preco * quantidadeVenda;

                        produto.quantidade =
                                produto.quantidade - quantidadeVenda;

                        faturamento = faturamento + total;
                        vendas++;
                        unidadesVendidas =
                                unidadesVendidas + quantidadeVenda;

                        System.out.println("Venda realizada!");
                        System.out.printf("Total: R$ %.2f%n", total);
                        System.out.println(
                                "Estoque restante: " + produto.quantidade
                        );
                    }
                    break;

                case 5:
                    System.out.println("\n--- ALERTAS ---");

                    int alertas = 0;

                    for (int j = 0; j < cadastrados; j++) {
                        Medicamento m = estoque[j];

                        if (m.validade < referencia) {
                            System.out.printf(
                                    "VENCIDO: %s | Validade: %02d%04d%n",
                                    m.nome, m.validade % 100,
                                    m.validade / 100
                            );
                            alertas++;
                        }

                        if (m.quantidade <= 5) {
                            System.out.println(
                                    "ESTOQUE BAIXO: " + m.nome +
                                            " | Unidades: " + m.quantidade
                            );
                            alertas++;
                        }
                    }

                    if (alertas == 0) {
                        System.out.println("Nenhum alerta!");
                    }
                    break;

                case 6:
                    System.out.println("\n--- RELATORIO ---");

                    int totalEstoque = 0;
                    int vencidos = 0;
                    int estoqueBaixo = 0;

                    for (int j = 0; j < cadastrados; j++) {
                        Medicamento m = estoque[j];

                        totalEstoque = totalEstoque + m.quantidade;

                        if (m.validade < referencia) {
                            vencidos++;
                        }

                        if (m.quantidade <= 5) {
                            estoqueBaixo++;
                        }
                    }

                    System.out.println(
                            "Medicamentos cadastrados: " + cadastrados
                    );
                    System.out.println(
                            "Unidades em estoque: " + totalEstoque
                    );
                    System.out.println(
                            "Medicamentos vencidos: " + vencidos
                    );
                    System.out.println(
                            "Estoque baixo: " + estoqueBaixo
                    );
                    System.out.println(
                            "Vendas realizadas: " + vendas
                    );
                    System.out.println(
                            "Unidades vendidas: " + unidadesVendidas
                    );
                    System.out.printf(
                            "Faturamento: R$ %.2f%n", faturamento
                    );
                    break;

                case 0:
                    System.out.println("Encerrando FarmaControl...");
                    break;

                default:
                    System.out.println("Opcao invalida!");
            }

        } while (opcao != 0);

        scanner.close();
    }
}
