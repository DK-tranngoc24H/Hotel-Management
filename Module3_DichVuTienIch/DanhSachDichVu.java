package BT_quanLyKhachSan;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DanhSachDichVu {
    private static final String TEN_FILE = "dichvu.txt";

    private final ArrayList<DichVu> ds = new ArrayList<>();

    // Khi tạo đối tượng, đọc dữ liệu từ file
    public DanhSachDichVu() {
        docFile();
    }

    public ArrayList<DichVu> getDs() {
        return ds;
    }

    // Đọc toàn bộ file, mỗi dòng thành một dịch vụ
    public void docFile() {
        ds.clear();
        if (!Files.exists(Paths.get(TEN_FILE))) {
            System.out.println("Chưa có file " + TEN_FILE + ", danh sách trống.");
            return;
        }
        try {
            List<String> cacDong = Files.readAllLines(Paths.get(TEN_FILE), StandardCharsets.UTF_8);
            for (String dong : cacDong) {
                try {
                    DichVu dv = tachDong(dong);
                    if (dv != null) {
                        ds.add(dv);
                    }
                } catch (Exception e) {
                    System.out.println("Bỏ qua dòng sai định dạng: " + dong);
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }

    // Ghi toàn bộ danh sách ra file
    public void ghiFile() {
        List<String> cacDong = new ArrayList<>();
        for (DichVu dv : ds) {
            cacDong.add(dv.toFileLine());
        }
        try {
            Files.write(Paths.get(TEN_FILE), cacDong, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    // Biến một dòng văn bản thành đối tượng dịch vụ đúng loại
    private DichVu tachDong(String dong) {
        // Xóa ký tự BOM nếu file được lưu bằng Notepad có BOM
        dong = dong.replace("\uFEFF", "").trim();
        if (dong.isEmpty()) {
            return null;
        }

        String[] t = dong.split("\\|");
        switch (t[0]) {
            case "AnUong":
                return new DichVuAnUong(t[1], t[2], Double.parseDouble(t[3]), t[4], t[5]);
            case "SpaGiaiTri":
                return new DichVuSpaGiaiTri(t[1], t[2], Double.parseDouble(t[3]), t[4], Integer.parseInt(t[5]));
            case "ThueXe":
                return new DichVuThueXe(t[1], t[2], Double.parseDouble(t[3]), t[4], Integer.parseInt(t[5]));
            default:
                return null;
        }
    }

    // In bảng dịch vụ có khung, các cột căn thẳng hàng
    public void hienThi() {
        if (ds.isEmpty()) {
            System.out.println("  Danh sách trống!");
            return;
        }

        // Tính độ rộng từng cột theo nội dung dài nhất
        int wSTT = 3, wMa = 3, wTen = 10, wGia = 8, wChiTiet = 10;
        for (DichVu dv : ds) {
            wMa = Math.max(wMa, dv.getMaDV().length());
            wTen = Math.max(wTen, dv.getTenDV().length());
            wGia = Math.max(wGia, String.format("%,.0f", dv.getDonGia()).length());
            wChiTiet = Math.max(wChiTiet, dv.moTaChiTiet().length());
        }

        String duong = "+" + "-".repeat(wSTT + 2) + "+" + "-".repeat(wMa + 2) + "+"
                + "-".repeat(wTen + 2) + "+" + "-".repeat(wGia + 2) + "+"
                + "-".repeat(wChiTiet + 2) + "+";

        System.out.println();
        System.out.println("  DANH SÁCH DỊCH VỤ KHÁCH SẠN (" + ds.size() + " dịch vụ)");
        System.out.println(duong);
        System.out.println("| " + canGiua("STT", wSTT) + " | " + canGiua("Mã", wMa)
                + " | " + canGiua("Tên dịch vụ", wTen) + " | "
                + canGiua("Đơn giá", wGia) + " | " + canGiua("Chi tiết", wChiTiet) + " |");
        System.out.println(duong);

        for (int i = 0; i < ds.size(); i++) {
            DichVu dv = ds.get(i);
            System.out.println("| " + canGiua(String.valueOf(i + 1), wSTT)
                    + " | " + canGiua(dv.getMaDV(), wMa)
                    + " | " + canTrai(dv.getTenDV(), wTen)
                    + " | " + canPhai(String.format("%,.0f", dv.getDonGia()), wGia)
                    + " | " + canTrai(dv.moTaChiTiet(), wChiTiet) + " |");
        }
        System.out.println(duong);
    }

    private String canGiua(String s, int w) {
        int cach = w - s.length();
        int trai = cach / 2;
        return " ".repeat(trai) + s + " ".repeat(cach - trai);
    }

    private String canTrai(String s, int w) {
        return s + " ".repeat(w - s.length());
    }

    private String canPhai(String s, int w) {
        return " ".repeat(w - s.length()) + s;
    }
}
