import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

public class caTruc {
    Scanner sc = new Scanner(System.in);
    private String maCa;
    private String tenCa;
    private String gioBatDau;
    private String gioKetThuc;
    private String ngayTruc;

    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("H:mm");
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    public caTruc() {
        this.maCa = "";
        this.tenCa = "";
        this.gioBatDau = "";
        this.gioKetThuc = "";
        this.ngayTruc = "";
    }

    public caTruc(String maCa, String tenCa, String gioBatDau, String gioKetThuc, String ngayTruc) {
        this.maCa = "";
        this.tenCa = "";
        this.gioBatDau = "";
        this.gioKetThuc = "";
        this.ngayTruc = "";
        setmaCa(maCa);
        setTenCa(tenCa);
        setGioBatDau(gioBatDau);
        setGioKetThuc(gioKetThuc);
        setNgayTruc(ngayTruc);
    }

    public static boolean kiemTraGio(String gio) {
        if (gio == null || gio.trim().isEmpty()) {
            return false;
        }
        try {
            LocalTime.parse(gio.trim(), TIME_FMT);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean kiemTraKhoangGio(String gioBatDau, String gioKetThuc) {
        if (!kiemTraGio(gioBatDau) || !kiemTraGio(gioKetThuc)) {
            return false;
        }
        LocalTime t1 = LocalTime.parse(gioBatDau.trim(), TIME_FMT);
        LocalTime t2 = LocalTime.parse(gioKetThuc.trim(), TIME_FMT);
        return t2.isAfter(t1);
    }

    public static boolean kiemTraNgay(String ngay) {
        if (ngay == null || ngay.trim().isEmpty()) {
            return false;
        }
        try {
            LocalDate.parse(ngay.trim(), DATE_FMT);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public void nhap() {
        while (true) {
            this.maCa = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ma ca: ");
            if (this.maCa.matches("^[A-Za-z0-9_-]+$")) {
                break;
            }
            ThongBao.inLoi("Ma ca chi duoc chua chu cai, so, '_' hoac '-' (khong khoang trang)!");
        }

        while (true) {
            this.tenCa = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ten ca (Ca sang/chieu/toi): ");
            if (!this.tenCa.contains(";")) {
                break;
            }
            ThongBao.inLoi("Ten ca khong duoc chua ky tu ';'!");
        }

        while (true) {
            String gio = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap gio bat dau (HH:mm, vi du 07:30): ");
            if (kiemTraGio(gio)) {
                this.gioBatDau = gio;
                break;
            }
            ThongBao.inLoi("Gio bat dau khong hop le! Dinh dang dung la HH:mm (00:00 - 23:59).");
        }

        while (true) {
            String gio = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap gio ket thuc (HH:mm, vi du 11:30): ");
            if (!kiemTraGio(gio)) {
                ThongBao.inLoi("Gio ket thuc khong hop le! Dinh dang dung la HH:mm (00:00 - 23:59).");
                continue;
            }
            if (!kiemTraKhoangGio(this.gioBatDau, gio)) {
                ThongBao.inLoi("Gio ket thuc phai sau gio bat dau (" + this.gioBatDau + ")!");
                continue;
            }
            this.gioKetThuc = gio;
            break;
        }

        while (true) {
            String ngay = KiemTraLoi.nhapChuoiKhongRong(sc, "Nhap ngay truc (dd/MM/yyyy): ");
            if (kiemTraNgay(ngay)) {
                this.ngayTruc = ngay;
                break;
            }
            ThongBao.inLoi("Ngay truc khong hop le! Dinh dang dung la dd/MM/yyyy (vi du 15/08/2026).");
        }
    }

    public void xuat() {
        System.out.println("Ma Ca: " + maCa + " | Ten Ca: " + tenCa + " | Thoi Gian: " + gioBatDau + " - " + gioKetThuc + " | Ngay Truc: " + ngayTruc);
    }

    public String getMaCa() {
        return maCa;
    }

    public String getTenCa() {
        return tenCa;
    }

    public void setTenCa(String tenCa) {
        if (tenCa == null || tenCa.trim().isEmpty()) {
            ThongBao.inLoi("Ten ca khong duoc de trong!");
            return;
        }
        if (tenCa.contains(";")) {
            ThongBao.inLoi("Ten ca khong duoc chua ky tu ';'!");
            return;
        }
        this.tenCa = tenCa.trim();
    }

    public String getGioBatDau() {
        return gioBatDau;
    }

    public String getGioKetThuc() {
        return gioKetThuc;
    }

    public String getNgayTruc() {
        return ngayTruc;
    }

    public void setmaCa(String maCa) {
        if (maCa == null || maCa.trim().isEmpty()) {
            ThongBao.inLoi("Ma ca khong duoc de trong!");
            return;
        }
        if (!maCa.trim().matches("^[A-Za-z0-9_-]+$")) {
            ThongBao.inLoi("Ma ca chi duoc chua chu cai, so, '_' hoac '-' (khong khoang trang)!");
            return;
        }
        this.maCa = maCa.trim();
    }

    public void setGioBatDau(String gioBatDau) {
        if (!kiemTraGio(gioBatDau)) {
            ThongBao.inLoi("Gio bat dau khong hop le (HH:mm)!");
            return;
        }
        if (!this.gioKetThuc.isEmpty() && !kiemTraKhoangGio(gioBatDau, this.gioKetThuc)) {
            ThongBao.inLoi("Gio bat dau phai truoc gio ket thuc (" + this.gioKetThuc + ")!");
            return;
        }
        this.gioBatDau = gioBatDau.trim();
    }

    public void setGioKetThuc(String gioKetThuc) {
        if (!kiemTraGio(gioKetThuc)) {
            ThongBao.inLoi("Gio ket thuc khong hop le (HH:mm)!");
            return;
        }
        if (!this.gioBatDau.isEmpty() && !kiemTraKhoangGio(this.gioBatDau, gioKetThuc)) {
            ThongBao.inLoi("Gio ket thuc phai sau gio bat dau (" + this.gioBatDau + ")!");
            return;
        }
        this.gioKetThuc = gioKetThuc.trim();
    }

    public void setNgayTruc(String ngayTruc) {
        if (!kiemTraNgay(ngayTruc)) {
            ThongBao.inLoi("Ngay truc khong hop le (dd/MM/yyyy)!");
            return;
        }
        this.ngayTruc = ngayTruc.trim();
    }
}
