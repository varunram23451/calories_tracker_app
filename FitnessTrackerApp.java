import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class FitnessTrackerApp extends JFrame {
    private JTextField stepsField, distanceField, weightField, goalStepsField;
    private JComboBox<String> activityTypeBox;
    private JButton trackButton, resetButton;
    private JLabel resultLabel, tipLabel;
    private JProgressBar goalProgressBar;

    private String[] tips = {
        "Stay hydrated 💧",
        "Stretch every hour 🧘",
        "Take the stairs 🚶",
        "Eat clean, stay lean 🥗",
        "Be stronger than excuses 💪",
        "No pain, no gain 🏋️",
        "Push yourself daily 🔥",
        "Discipline = Freedom 🎯"
    };

    public FitnessTrackerApp() {
        setTitle("🏃 Fitness Tracker Pro");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Load the full background image
        ImageIcon backgroundImage = new ImageIcon("gym_bg.jpg"); // <-- Replace with your actual filename
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout());
        setContentPane(backgroundLabel); // set the label as the content pane

        Font titleFont = new Font("SansSerif", Font.BOLD, 22);
        Font labelFont = new Font("SansSerif", Font.BOLD, 15);

        JLabel titleLabel = new JLabel("📦 Be Consistent and Trust the process 📦", JLabel.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.BLACK); // white for contrast
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        backgroundLabel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false); // make it transparent to show background
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        stepsField = new JTextField(10);
        distanceField = new JTextField(10);
        weightField = new JTextField(10);
        goalStepsField = new JTextField("10000", 10);
        activityTypeBox = new JComboBox<>(new String[]{"Walking 🏃", "Running 🏃‍♂️", "Cycling 🚴"});
        trackButton = new JButton("Track Activity ✅");
        resetButton = new JButton("Reset 🔄");

        // Bold + Creative Labels
        JLabel[] labels = {
            new JLabel("👣 Total Steps:"),
            new JLabel("✏️ Distance Travelled (km):"),
            new JLabel("⚖️ Your Weight (kg):"),
            new JLabel("🎽 Choose Activity:"),
            new JLabel("🎯 Daily Step Goal:")
        };

        JTextField[] fields = {stepsField, distanceField, weightField, goalStepsField};

        for (int i = 0; i < labels.length; i++) {
            labels[i].setFont(labelFont);
            labels[i].setForeground(Color.BLACK); // white for better visibility
            gbc.gridx = 0;
            gbc.gridy = i;
            formPanel.add(labels[i], gbc);

            gbc.gridx = 1;
            if (i == 3) {
                formPanel.add(activityTypeBox, gbc);
            } else {
                formPanel.add(fields[i < 3 ? i : i - 1], gbc);
            }
        }

        gbc.gridx = 0;
        gbc.gridy = labels.length;
        formPanel.add(trackButton, gbc);

        gbc.gridx = 1;
        formPanel.add(resetButton, gbc);

        backgroundLabel.add(formPanel, BorderLayout.CENTER);

        // Result + tip
        resultLabel = new JLabel(" ");
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        resultLabel.setForeground(Color.YELLOW);
        tipLabel = new JLabel("Tip: " + tips[0]);
        tipLabel.setFont(labelFont);
        tipLabel.setForeground(Color.GREEN);

        JPanel bottomPanel = new JPanel(new GridLayout(3, 1));
        bottomPanel.setOpaque(false); // transparent
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        goalProgressBar = new JProgressBar(0, 100);
        goalProgressBar.setStringPainted(true);
        goalProgressBar.setForeground(new Color(46, 204, 113));

        bottomPanel.add(resultLabel);
        bottomPanel.add(goalProgressBar);
        bottomPanel.add(tipLabel);
        backgroundLabel.add(bottomPanel, BorderLayout.SOUTH);

        // Button actions
        trackButton.addActionListener(e -> {
            try {
                int steps = Integer.parseInt(stepsField.getText().trim());
                double distance = Double.parseDouble(distanceField.getText().trim());
                double weight = Double.parseDouble(weightField.getText().trim());
                int goal = Integer.parseInt(goalStepsField.getText().trim());
                String activity = (String) activityTypeBox.getSelectedItem();

                double calories = calculateCaloriesBurned(steps, distance, weight, activity);
                resultLabel.setText(String.format("🔥 Calories Burned: %.2f kcal", calories));

                int progress = (int) (((double) steps / goal) * 100);
                goalProgressBar.setValue(Math.min(progress, 100));

                int randomIndex = new Random().nextInt(tips.length);
                tipLabel.setText("Tip: " + tips[randomIndex]);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter valid numeric values for steps, distance, weight, and goal.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        resetButton.addActionListener(e -> {
            stepsField.setText("");
            distanceField.setText("");
            weightField.setText("");
            goalStepsField.setText("10000");
            activityTypeBox.setSelectedIndex(0);
            goalProgressBar.setValue(0);
            resultLabel.setText(" ");
            tipLabel.setText("Tip: " + tips[0]);
        });
    }

    private double calculateCaloriesBurned(int steps, double distance, double weight, String activity) {
        double met;
        switch (activity) {
            case "Walking 🏃": met = 3.5; break;
            case "Running 🏃‍♂️": met = 7.0; break;
            case "Cycling 🚴": met = 6.0; break;
            default: met = 3.5;
        }

        double speed = switch (activity) {
            case "Running 🏃‍♂️" -> 8.0;
            case "Cycling 🚴" -> 15.0;
            default -> 5.0;
        };

        double time = distance / speed;
        return met * weight * time;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FitnessTrackerApp().setVisible(true));
    }
}
