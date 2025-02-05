import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    public static void main(String[] args) {

        // Inserindo todos os funcionários
        List<Funcionario> listaDeFuncionarios = new ArrayList<>(List.of(
                new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
                new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
                new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
                new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
                new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
                new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
                new Funcionario("Artur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
                new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
                new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
                new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        ));

        // Lista completa
        listaDeFuncionarios.forEach(System.out::println);
        System.out.println("\n");

        // Removendo o João da lista de funcionários
        listaDeFuncionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        // Aumento de salário em 10%
        listaDeFuncionarios.forEach(Principal::aumentoDeSalario);
        System.out.println("\n");

        // Agrupando os funcionários por função
        Map<String, List<Funcionario>> funcionariosPorFuncao = listaDeFuncionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // Exibindo os funcionários agrupados por função
        funcionariosPorFuncao.forEach(Principal::imprimirFuncionariosPorFuncao);
        System.out.println("\n");

        // Funcionários que fazem aniversário em Outubro ou Dezembro
        imprimirAniversariantes(listaDeFuncionarios);

        System.out.println("\n");

        // Funcionários maiores de idade
        imprimirMaioresDeIdade(listaDeFuncionarios);

        System.out.println("\n");

        // Ordenando a lista de funcionários pelo nome
        listaDeFuncionarios.sort(Comparator.comparing(Funcionario::getNome));
        listaDeFuncionarios.forEach(System.out::println);

        System.out.println("\n");

        // Soma dos salários de todos os funcionários
        BigDecimal totalSalario = listaDeFuncionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Formatação do valor
        NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);

        System.out.println("Soma dos salários de todos os funcionários");
        System.out.println("O salário total é: " + nf.format(totalSalario));

        System.out.println("\n");

        // Quantos salários mínimos cada pessoa recebe
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        listaDeFuncionarios.forEach(f -> {
            BigDecimal salariosMinimos = f.getSalario().divide(salarioMinimo, 0, RoundingMode.HALF_UP);
            System.out.println(f.getNome() + " ganha " + salariosMinimos + " salários mínimos.");
        });

    }

    public static void aumentoDeSalario(Funcionario funcionario) {
        BigDecimal aumento = funcionario.getSalario().multiply(new BigDecimal("0.10"));
        funcionario.setSalario(funcionario.getSalario().add(aumento));
        System.out.println(funcionario);
    }

    public static void imprimirFuncionariosPorFuncao(String funcao, List<Funcionario> funcionarios) {
        System.out.println("Função: " + funcao);
        funcionarios.forEach(System.out::println);
    }

    public static void imprimirAniversariantes(List<Funcionario> listaDeFuncionarios) {
        listaDeFuncionarios.stream()
                .filter(f -> f.getDataNascimento().getMonth() == Month.OCTOBER || f.getDataNascimento().getMonth() == Month.DECEMBER)
                .forEach(System.out::println);
    }

    public static void imprimirMaioresDeIdade(List<Funcionario> listaDeFuncionarios) {
        listaDeFuncionarios.stream()
                .filter(f -> Period.between(f.getDataNascimento(), LocalDate.now()).getYears() >= 18)
                .forEach(f -> {
                    int idade = Period.between(f.getDataNascimento(), LocalDate.now()).getYears();
                    System.out.println("Nome: " + f.getNome() + " | Idade: " + idade);
                });
    }
}
