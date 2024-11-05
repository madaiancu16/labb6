package exercitiul1;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MainApp {
    public static void main(String[] args) {
        try {
            List<Angajat> angajati = AngajatService.citesteAngajati();
            Scanner scanner = new Scanner(System.in);
            int anulCurent = Year.now().getValue();
            int anulTrecut = anulCurent - 1;

            while (true) {
                System.out.println("\nMeniu:");
                System.out.println("1. Afișare lista angajați");
                System.out.println("2. Afișare angajați cu salariu peste 2500 RON");
                System.out.println("3. Lista angajaților din aprilie anul trecut cu funcții de conducere");
                System.out.println("4. Afișare angajați fără funcții de conducere, ordonați descrescător după salariu");
                System.out.println("5. Afișare nume angajați în majuscule");
                System.out.println("6. Afișare salarii mai mici de 3000 RON");
                System.out.println("7. Afișare data primului angajat");
                System.out.println("8. Afișare statistici salarii");
                System.out.println("9. Verificare existență angajat numit \"Ion\"");
                System.out.println("10. Afișare număr de angajați angajați vara anului trecut");
                System.out.println("0. Ieșire");
                System.out.print("Alege opțiunea: ");

                int optiune = scanner.nextInt();
                switch (optiune) {
                    case 1:
                        angajati.forEach(System.out::println);
                        break;

                    case 2:
                        angajati.stream()
                                .filter(a -> a.getSalariu() > 2500)
                                .forEach(System.out::println);
                        break;

                    case 3:
                        List<Angajat> angajatiaprilie = angajati.stream()
                                .filter(a -> a.getDataAngajarii().getYear() == anulTrecut &&
                                        a.getDataAngajarii().getMonth() == Month.APRIL &&
                                        (a.getPost().toLowerCase().contains("sef") ||
                                                a.getPost().toLowerCase().contains("director")))
                                .collect(Collectors.toList());
                        angajatiaprilie.forEach(System.out::println);
                        break;

                    case 4:
                        angajati.stream()
                                .filter(a -> !a.getPost().toLowerCase().contains("director") &&
                                        !a.getPost().toLowerCase().contains("sef"))
                                .sorted(Comparator.comparing(Angajat::getSalariu).reversed())
                                .forEach(System.out::println);
                        break;

                    case 5:
                        angajati.stream()
                                .map(a -> a.getNume().toUpperCase())
                                .forEach(System.out::println);
                        break;

                    case 6:
                        angajati.stream()
                                .map(Angajat::getSalariu)
                                .filter(s -> s < 3000)
                                .forEach(System.out::println);
                        break;

                    case 7:
                        Optional<Angajat> primulang = angajati.stream()
                                .min(Comparator.comparing(Angajat::getDataAngajarii));
                        primulang.ifPresentOrElse(
                                System.out::println,
                                () -> System.out.println("Nu există angajați."));
                        break;

                    case 8:
                        var statistici = angajati.stream()
                                .collect(Collectors.summarizingDouble(Angajat::getSalariu));
                        System.out.println("Salariu mediu: " + statistici.getAverage());
                        System.out.println("Salariu minim: " + statistici.getMin());
                        System.out.println("Salariu maxim: " + statistici.getMax());
                        break;

                    case 9:
                        angajati.stream()
                                .map(Angajat::getNume)
                                .filter(nume -> nume.contains("Ion"))
                                .findAny()
                                .ifPresentOrElse(
                                        nume -> System.out.println("Firma are cel puțin un Ion angajat"),
                                        () -> System.out.println("Firma nu are nici un Ion angajat"));
                        break;

                    case 10:
                        long nr = angajati.stream()
                                .filter(a -> a.getDataAngajarii().getYear() == anulTrecut &&
                                        (a.getDataAngajarii().getMonth() == Month.JUNE ||
                                                a.getDataAngajarii().getMonth() == Month.JULY ||
                                                a.getDataAngajarii().getMonth() == Month.AUGUST))
                                .count();
                        System.out.println("Număr angajați vara anului trecut: " + nr);
                        break;

                    case 0:
                        System.out.println("Ieșire din program.");
                        return;

                    default:
                        System.out.println("Opțiune invalidă. Încearcă din nou.");
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Eroare la citirea fișierului JSON: " + e.getMessage());
        }
    }
}
