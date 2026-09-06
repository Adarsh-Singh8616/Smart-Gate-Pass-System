package com.niet.gatepass.dao;

import com.niet.gatepass.model.Hostel;
import com.niet.gatepass.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HostelDAO {

    public List<Hostel> getAllHostels() throws SQLException {
        List<Hostel> list = new ArrayList<>();
        String sql = "SELECT * FROM hostels ORDER BY hostel_id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public Hostel getHostelById(int hostelId) throws SQLException {
        String sql = "SELECT * FROM hostels WHERE hostel_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, hostelId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    private Hostel mapRow(ResultSet rs) throws SQLException {
        Hostel h = new Hostel();
        h.setHostelId(rs.getInt("hostel_id"));
        h.setHostelName(rs.getString("hostel_name"));
        h.setTotalRooms(rs.getInt("total_rooms"));
        h.setHostelFee(rs.getBigDecimal("hostel_fee"));
        h.setMessFee(rs.getBigDecimal("mess_fee"));
        h.setFacilities(rs.getString("facilities"));
        return h;
    }
}
