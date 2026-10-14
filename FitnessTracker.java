import java.io.*;
import java.util.*;

public class FitnessTracker {

    // CO1: formula (problem -> program)
    static double bmi(double kg, double cm) {
        double m = cm / 100;
        return kg / (m * m);
    }

    // CO2: switch to choose the MET value of an activity
    static double met(String activity) {
        switch (activity.toLowerCase()) {
            case "running": return 9.8;
            case "walking": return 3.5;
            case "cycling": return 7.5;
            default:        return 4.0;
        }
    }

    // CO3: recursion on an array
    static int totalSteps(int[] steps, int n) {
        if (n == 0) return 0;
        return steps[n - 1] + totalSteps(steps, n - 1);
    }

    // CO4: fail safely with an exception
    static void checkMinutes(int minutes) {
        if (minutes <= 0) throw new IllegalArgumentException("Minutes must be positive");
    }

    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);

        // CO1: input -> process -> output
        System.out.print("Weight (kg): ");
        double kg = sc.nextDouble();
        System.out.print("Height (cm): ");
        double cm = sc.nextDouble();
        double b = bmi(kg, cm);

        // CO2: if-else,else-if, switch-case
        String category;
        if (b < 18.5) category = "Underweight";
        else if (b < 25) category = "Normal";
        else if (b < 30) category = "Overweight";
        else category = "Obese";
        System.out.printf("BMI: %.1f (%s)%n", b, category);
        {
            // CO3: array
        int[] steps = new int[7];
        System.out.print("Enter steps for 7 days: ");
        for (int i = 0; i < 7; i++) steps[i] = sc.nextInt();
        System.out.println("Total steps: " + totalSteps(steps, 7));
        }
        // CO4: ArrayList + String methods
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Double> burned = new ArrayList<>();
        System.out.print("How many workouts today? ");
        int count = sc.nextInt();
        for (int i = 1; i <= count; i++) {
            System.out.print("Workout " + i + " (activity minutes): ");
            String activity = sc.next();
            int minutes = sc.nextInt();
            try {
                checkMinutes(minutes);
                names.add(activity.toUpperCase());
                burned.add(met(activity) * kg * minutes / 60.0);
            } catch (IllegalArgumentException e) {
                System.out.println("Skipped: " + e.getMessage());
            }
        }
        for (int i = 0; i < names.size(); i++)
            System.out.printf("%s: %.1f kcal%n", names.get(i), burned.get(i));
        if (!burned.isEmpty())
            System.out.printf("Highest burn: %.1f kcal%n", Collections.max(burned));

        

       
        // CO5: save to a file
        try (FileWriter fw = new FileWriter("workouts.txt")) {
            for (int i = 0; i < names.size(); i++)
                fw.write(names.get(i) + "," + burned.get(i) + "\n");
            System.out.println("Saved to workouts.txt");
        } catch (IOException e) {
            System.out.println("Could not save file.");
        }
        // CO6: stream to total the calories
        double total = burned.stream().mapToDouble(Double::doubleValue).sum();
        System.out.printf("Total calories burned: %.1f%n", total);
    }
}