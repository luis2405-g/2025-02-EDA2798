import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

import model.Account;
import model.Balance;
import model.Cards;
import model.Loans;
import services.*;

public class App {

    private static final AccountService accountService = new AccountService();
    private static final BalanceService balanceService = new BalanceService();
    private static final LoansService loansService = new LoansService();
    private static final CardService cardService = new CardService();

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                printMainMenu();
                String option = sc.nextLine().trim();
                switch (option) {
                    case "1":
                        runCrudMenu(sc, "Account");
                        break;
                    case "2":
                        runCrudMenu(sc, "Balance");
                        break;
                    case "3":
                        runCrudMenu(sc, "Loans");
                        break;
                    case "4":
                        runCrudMenu(sc, "Cards");
                        break;
                    case "0":
                        running = false;
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\n=== Menú Principal ===");
        System.out.println("1. Account");
        System.out.println("2. Balance");
        System.out.println("3. Loans");
        System.out.println("4. Cards");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void runCrudMenu(Scanner sc, String entityName) {
        boolean back = false;
        while (!back) {
            printCrudMenu(entityName);
            String opt = sc.nextLine().trim();

            switch (opt) {
                case "1":
                    createEntity(sc, entityName);
                    break;
                case "2":
                    System.out.print("Ingrese ID: ");
                    String id = sc.nextLine().trim();
                    readEntity(entityName, id);
                    break;
                case "3":
                    listAllEntities(entityName);
                    break;
                case "4":
                    System.out.println("Actualizar — use la opción Crear para sobrescribir un registro existente.");
                    break;
                case "5":
                    System.out.print("Ingrese ID a eliminar: ");
                    String idDel = sc.nextLine().trim();
                    deleteEntity(entityName, idDel);
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void printCrudMenu(String entityName) {
        System.out.println("\n--- " + entityName + " CRUD ---");
        System.out.println("1. Create / Update");
        System.out.println("2. Read by ID");
        System.out.println("3. List All");
        System.out.println("4. Update (alias of Create)");
        System.out.println("5. Delete");
        System.out.println("0. Back");
        System.out.print("Seleccione una opción: ");
    }

    // ====================================
    // MÉTODOS CRUD GENÉRICOS
    // ====================================

    private static void createEntity(Scanner sc, String entityName) {
        switch (entityName) {
            case "Account":
                System.out.print("Número de cuenta: ");
                String accNum = sc.nextLine();
                System.out.print("Propietario: ");
                String owner = sc.nextLine();
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Teléfono: ");
                String phone = sc.nextLine();
                System.out.print("Tipo de cuenta: ");
                String type = sc.nextLine();
                System.out.print("Dirección: ");
                String address = sc.nextLine();
                accountService.save(new Account(accNum, owner, email, phone, type, address));
                break;

            case "Balance":
                System.out.print("Descripción: ");
                String desc = sc.nextLine();
                System.out.print("Ingreso (cashIn): ");
                BigDecimal in = new BigDecimal(sc.nextLine());
                System.out.print("Egreso (cashOut): ");
                BigDecimal out = new BigDecimal(sc.nextLine());
                System.out.print("Saldo final (closingBalance): ");
                BigDecimal closing = new BigDecimal(sc.nextLine());
                balanceService.save(new Balance(LocalDate.now(), desc, in, out, closing));
                break;

            case "Loans":
                System.out.print("Tipo de préstamo (Home/Vehicle/University/Personal): ");
                String ltype = sc.nextLine();
                System.out.print("Monto total: ");
                BigDecimal total = new BigDecimal(sc.nextLine());
                System.out.print("Monto pagado: ");
                BigDecimal paid = new BigDecimal(sc.nextLine());
                System.out.print("Monto pendiente: ");
                BigDecimal pending = new BigDecimal(sc.nextLine());
                loansService.save(new Loans(LocalDate.now(), ltype, total, paid, pending));
                break;

            case "Cards":
                System.out.print("Número de tarjeta: ");
                String cnum = sc.nextLine();
                System.out.print("Tipo (Debit/Credit): ");
                String ctype = sc.nextLine();
                System.out.print("Límite total: ");
                BigDecimal limit = new BigDecimal(sc.nextLine());
                System.out.print("Monto usado: ");
                BigDecimal used = new BigDecimal(sc.nextLine());
                System.out.print("Disponible: ");
                BigDecimal avail = new BigDecimal(sc.nextLine());
                cardService.save(new Cards(cnum, ctype, limit, used, avail));
                break;
        }
    }

    private static void readEntity(String entityName, String id) {
        switch (entityName) {
            case "Account":
                accountService.findById(id)
                        .ifPresentOrElse(System.out::println, () -> System.out.println("No encontrado."));
                break;
            case "Balance":
                balanceService.findById(id)
                        .ifPresentOrElse(System.out::println, () -> System.out.println("No encontrado."));
                break;
            case "Loans":
                loansService.findById(id)
                        .ifPresentOrElse(System.out::println, () -> System.out.println("No encontrado."));
                break;
            case "Cards":
                cardService.findById(id)
                        .ifPresentOrElse(System.out::println, () -> System.out.println("No encontrado."));
                break;
        }
    }

    private static void listAllEntities(String entityName) {
        switch (entityName) {
            case "Account":
                accountService.findAll().forEach(System.out::println);
                break;
            case "Balance":
                balanceService.findAll().forEach(System.out::println);
                break;
            case "Loans":
                loansService.findAll().forEach(System.out::println);
                break;
            case "Cards":
                cardService.findAll().forEach(System.out::println);
                break;
        }
    }

    private static void deleteEntity(String entityName, String id) {
        boolean deleted = false;
        switch (entityName) {
            case "Account":
                deleted = accountService.deleteById(id);
                break;
            case "Balance":
                deleted = balanceService.deleteById(id);
                break;
            case "Loans":
                deleted = loansService.deleteById(id);
                break;
            case "Cards":
                deleted = cardService.deleteById(id);
                break;
        }
        System.out.println(deleted ? "Eliminado correctamente." : "No se encontró el registro.");
    }
}
