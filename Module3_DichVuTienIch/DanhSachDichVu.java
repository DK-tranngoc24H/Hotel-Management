


import java.util.ArrayList;

public class DanhSachDichVu {
    private ArrayList<DichVu> dsDichVu = new ArrayList<>();

  
    public DanhSachDichVu() {
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
        
        System.out.println("\n============================================ DANH SÁCH DỊCH VỤ KHÁCH SẠN ============================================");
        System.out.printf("%-5s | %-6s | %-25s | %-12s | %-30s\n", "STT", "Mã DV", "Tên Dịch Vụ", "Đơn Giá", "Chi Tiết / Thông Tin");
        System.out.println("----------------------------------------------------------------------------------------------------------------------");
        
        for (int i = 0; i < dsDichVu.size(); i++) {
            DichVu dv = dsDichVu.get(i);
            String thongTinChiTiet = "";
            
            // Phân loại để lấy thông tin chi tiết tương ứng mà không bị xuống dòng
            if (dv instanceof DichVuAnUong) {
                // Do DichVuAnUong đang lưu chung, ta có thể ép kiểu hoặc tuỳ biến
                thongTinChiTiet = "Ẩm thực (Đồ ăn / Nước uống)";
            } else if (dv instanceof DichVuSpaGiaiTri) {
                thongTinChiTiet = "Spa & Giải trí";
            } else if (dv instanceof DichVuThueXe) {
                thongTinChiTiet = "Thuê phương tiện";
            }
            
            // In gọn gàng trên đúng một dòng duy nhất
            System.out.printf("%-5s | %-6s | %-25s | %-12.0f | %-30s\n", 
                    "#" + (i + 1), 
                    dv.getMaDV(), 
                    dv.getTenDV(), 
                    dv.getDonGia(),
                    dv.getTenDV() // Hoặc hiển thị mô tả ngắn gọn
            );
        }
        System.out.println("======================================================================================================================");
    }
    
    public ArrayList<DichVu> getDsDichVu() {
        return dsDichVu;
    }

    public DichVu timKiemTheoMa(String maDV) {
        if (maDV == null) return null;
        for (DichVu dv : dsDichVu) {
            if (dv.getMaDV() != null && dv.getMaDV().equalsIgnoreCase(maDV.trim())) {
                return dv;
            }
        }
        return null;
    }

    public DichVu timKiemTheoTen(String tenDV) {
        if (tenDV == null) return null;
        for (DichVu dv : dsDichVu) {
            if (dv.getTenDV() != null && dv.getTenDV().equalsIgnoreCase(tenDV.trim())) {
                return dv;
            }
        }
        return null;
    }
}
