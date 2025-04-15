package Tugas.TextFiles;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class App extends Application{
    private static final String NAMA_FILE = "24552011091_RifkySaputraMaylandra.txt";
    private static final String TARGET_DIR = "src/main/java/Tugas/TextFiles/";

    private String get_file_path() {
        File dir = new File(TARGET_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return TARGET_DIR + NAMA_FILE;
    }

    @Override
    public void start(Stage stage) {
        String file_path = get_file_path();

        TextField inputNama = new TextField();
        inputNama.setPromptText("Masukkan nama");

        TextField inputNIM = new TextField();
        inputNIM.setPromptText("Masukan NIM");

        Button tombolSimpan = new Button("Simpan ke File");
        Button tombolBaca = new Button("Baca dari File");
        Button tombolUbah = new Button("Ubah Data");

        TextArea areaTeks = new TextArea();
        areaTeks.setEditable(false);

        // Tombol Simpan
        tombolSimpan.setOnAction(e -> {
            try (FileWriter writer = new FileWriter(get_file_path(), true)) {
                String data = "Nama: " + inputNama.getText() +", NIM: " + inputNIM.getText();
                writer.write(data + System.lineSeparator());
                inputNama.clear();
                inputNIM.clear();
                areaTeks.setText("Berhasil menyimpan data.");
            } catch (IOException ex) {
                areaTeks.setText("Gagal menyimpan data.");
            }
        });

        // Tombol Baca
        tombolBaca.setOnAction(e -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(get_file_path()))) {
                StringBuilder isi = new StringBuilder();
                String baris;
                int nomor = 1;

                isi.append("=== Daftar Data ===\n\n");

                while ((baris = reader.readLine()) != null) {
                    String[] parts = baris.split(", ");
                    String nama = parts[0].replace("Nama: ", "");
                    String nim = parts[1].replace("NIM: ", "");

                    isi.append("Data ke-").append(nomor++).append(":\n")
                            .append("  Nama: ").append(nama).append("\n")
                            .append("  NIM : ").append(nim).append("\n\n");
                }

                if (nomor == 1) {
                    areaTeks.setText("File kosong. Tidak ada data yang ditemukan.");
                } else {
                    areaTeks.setText(isi.toString());
                }
            } catch (IOException ex) {
                areaTeks.setText("Gagal membaca file: " + ex.getMessage());
            } catch (ArrayIndexOutOfBoundsException ex) {
                areaTeks.setText("Format file tidak valid. Pastikan format: 'Nama: [nama], NIM: [nim]'");
            }
        });

        // Tombol Ubah
        tombolUbah.setOnAction(e -> {
            String namaInput = inputNama.getText();
            String nimInput = inputNIM.getText();

            List<String> isiBaru = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(get_file_path()))) {
                String baris;
                boolean ditemukan = false;
                while ((baris = reader.readLine()) != null) {
                    if (baris.contains("Nama: " + namaInput + ",")) {
                        baris = "Nama: " + namaInput + "Nim: " + nimInput;
                        ditemukan = true;
                    }
                    isiBaru.add(baris + System.lineSeparator());
                }

                if (!ditemukan) {
                    areaTeks.setText("Data dengan nama tersebut tidak ditemukan.");
                    return;
                }

            } catch (IOException ex) {
                areaTeks.setText("Gagal membaca file.");
                return;
            }

            // Tulis ulang file dengan data yang sudah diperbarui
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(get_file_path(), false))) {
                for (String baris : isiBaru) {
                    writer.write(baris);
                    writer.newLine();
                }
                areaTeks.setText("Data berhasil diubah.");
                inputNama.clear();
                inputNIM.clear();
            } catch (IOException ex) {
                areaTeks.setText("Gagal menyimpan perubahan.");
            }
        });

        VBox layout = new VBox(10, inputNama, inputNIM, tombolSimpan, tombolUbah, tombolBaca, areaTeks);
        layout.setStyle("-fx-padding: 20px");

        stage.setScene(new Scene(layout, 400, 400));
        stage.setTitle("Text File dengan JavaFX");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
