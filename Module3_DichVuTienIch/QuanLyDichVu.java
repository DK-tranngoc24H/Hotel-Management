package BT_quanLyKhachSan;


import java.util.ArrayList;

public class QuanLyDichVu {
    private ArrayList<DichVu> dsDichVu = new ArrayList<>();

    public void themDichVu(DichVu dv) {
        dsDichVu.add(dv);
        System.out.println("Thêm dịch vụ thành công!");
    }
    
    public QuanLyDichVu() {
        khoiTaoDuLieuMau();
    }

    public void khoiTaoDuLieuMau() {
        // Đồ ăn & Nước uống
        dsDichVu.add(new DichVuAnUong("DA01", "Phở bò đặc biệt", 50000, "Đồ ăn", "Bữa sáng"));
        dsDichVu.add(new DichVuAnUong("DA02", "Bún Bò", 50000, "Đồ ăn", "Bữa sáng"));
        dsDichVu.add(new DichVuAnUong("DA03", "Cơm tấm sườn bì chả ", 65000, "Đồ ăn", "Bữa trưa/tối"));
        dsDichVu.add(new DichVuAnUong("DA04", "Cơm gà xối mỡ", 65000, "Đồ ăn", "Bữa trưa/tối"));
        dsDichVu.add(new DichVuAnUong("DA05", "Lẩu Gà Lá é ", 200000, "Đồ ăn", "Bữa trưa/tối"));
        
        dsDichVu.add(new DichVuAnUong("NU01", "Cà phê sữa đá", 35000, "Đồ uống", "Không cồn - 280ml"));
        dsDichVu.add(new DichVuAnUong("NU02", "Nước ép cam tươi", 35000, "Đồ uống", "Không cồn - 330ml"));
        dsDichVu.add(new DichVuAnUong("NU03", "matcha latte", 35000, "Đồ uống", "Không cồn - 330ml"));
        dsDichVu.add(new DichVuAnUong("NU04", "Bia Heineken lon", 30000, "Đồ uống", "Có cồn - 330ml"));
        dsDichVu.add(new DichVuAnUong("NU05", "Rựu gạo ", 70000, "Đồ uống", "có cồn - 200ml"));

        // Spa & Giải trí
        dsDichVu.add(new DichVuSpaGiaiTri("SG01", "Massage body Thụy Điển", 300000, "Massage", 60));
        dsDichVu.add(new DichVuSpaGiaiTri("SG02", "Vé vào cổng Hồ bơi vô cực", 100000, "Hồ bơi", 120));
        dsDichVu.add(new DichVuSpaGiaiTri("SG03", "Phòng hát Karaoke VIP", 250000, "Karaoke", 60));
        dsDichVu.add(new DichVuSpaGiaiTri("SG04", "Vé tập Gym", 50000, "Phòng Gym", 180));
        

        // Thuê xe
        dsDichVu.add(new DichVuThueXe("TX01", "Thuê xe đạp", 50000, "Xe đạp", 1));
        dsDichVu.add(new DichVuThueXe("TX02", "Thuê xe máy tay ga", 150000, "Xe máy", 1));
        dsDichVu.add(new DichVuThueXe("TX03", "Thuê ô tô tự lái 4 chỗ", 850000, "Ô tô 4 chỗ", 1));
        dsDichVu.add(new DichVuThueXe("TX04", "Thuê ô tô 7 chỗ có tài xế", 1500000, "Ô tô 7 chỗ", 1));
    }

    public void hienThiDanhSach() {
        if (dsDichVu.isEmpty()) {
            System.out.println("Danh sách dịch vụ đang trống!");
            return;
        }
        
        System.out.println("\n============================= MENU DỊCH VỤ KHÁCH SẠN =============================");
        System.out.println("\n");
        int stt = 1;
        for (DichVu dv : dsDichVu) {
            System.out.print("[" + stt++ + "] ");
            dv.xuat();
            System.out.println("\n");
            System.out.println("----------------------------------------------------------------------------------");
        }
    }
    public DichVu timKiemDichVu(String ma) {
        for (DichVu dv : dsDichVu) {
            if (dv.getMaDV().equalsIgnoreCase(ma)) {
                return dv;
            }
        }
        return null;
    }

    public ArrayList<DichVu> getDsDichVu() {
        return dsDichVu;
    }
}