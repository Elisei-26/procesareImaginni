import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class BitPlaneSlicingApp extends JFrame {

    private BufferedImage originalImage;
    private BufferedImage processedImage;

    private JComboBox<Integer> bitDepthComboBox;
    private JButton openImageButton;
    private JButton processButton;
    private JLabel statusLabel;

    public BitPlaneSlicingApp() {
        super("Bit Plane Slicing App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 200);
        setLayout(new FlowLayout());

        bitDepthComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8});
        openImageButton = new JButton("Open Image");
        processButton = new JButton("Process");
        statusLabel = new JLabel("Status: ");

        add(new JLabel("Select Bit Depth:"));
        add(bitDepthComboBox);
        add(openImageButton);
        add(processButton);
        add(statusLabel);

        openImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openImage();
            }
        });

        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processImage();
            }
        });
    }

    private void openImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                originalImage = ImageIO.read(selectedFile);
                statusLabel.setText("Status: Image loaded successfully");
            } catch (IOException e) {
                statusLabel.setText("Status: Error loading image");
                e.printStackTrace();
            }
        }
    }


    // TODO: Implement display and save methods for processedImage//////////////////////////////////////////////////////
    private void displayImage(BufferedImage image, String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(image.getWidth(), image.getHeight());

        JLabel label = new JLabel(new ImageIcon(image));
        frame.add(label);

        frame.setVisible(true);
    }

    private void saveImage(BufferedImage image, String filePath) {
        try {
            ImageIO.write(image, "png", new File(filePath));
            statusLabel.setText("Status: Image saved successfully");
        } catch (IOException e) {
            statusLabel.setText("Status: Error saving image");
            e.printStackTrace();
        }
    }
    // TODO: Implement display and save methods for processedImage//////////////////////////////////////////////////////

    private void processImage() {
        if (originalImage == null) {
            statusLabel.setText("Status: Please open an image first");
            return;
        }

        // TODO: Implement display and save methods for processedImage//////////////////////////////////////////////////
        int selectedBit = (int) bitDepthComboBox.getSelectedItem();
        processedImage = extractBitPlane(originalImage, selectedBit);

        // Afisarea imaginii procesate
        displayImage(processedImage, "Processed Image");

        // Salvarea imaginii procesate
        saveImage(processedImage, "processed_image.png");
        // TODO: Implement display and save methods for processedImage//////////////////////////////////////////////////

        statusLabel.setText("Status: Image processed successfully");
    }

    private BufferedImage extractBitPlane(BufferedImage image, int bit) {
        // TODO: Implement the bit plane extraction logic
        // You can use BufferedImage.getRGB, Color.getRed, Color.getGreen, Color.getBlue methods
        // to extract RGB values and manipulate the bit plane.

        //return null; // Placeholder, replace with actual implementation
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int grayValue = (rgb >> 8) & 0xFF; // Extract 8-bit grayscale value

                // Extract the specified bit from the grayscale value
                int bitValue = (grayValue >> (8 - bit)) & 0x01;

                // Create a new RGB value with the extracted bit
                int newRgb = (bitValue == 1) ? 0xFFFFFF : 0x000000; // White for bit = 1, black for bit = 0

                resultImage.setRGB(x, y, newRgb);
            }
        }
        return resultImage;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BitPlaneSlicingApp().setVisible(true);
            }
        });
    }
}
