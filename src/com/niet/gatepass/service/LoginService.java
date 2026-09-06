package com.niet.gatepass.service;

import com.niet.gatepass.dao.ChiefWardenDAO;
import com.niet.gatepass.dao.StudentDAO;
import com.niet.gatepass.dao.WardenDAO;
import com.niet.gatepass.model.ChiefWarden;
import com.niet.gatepass.model.Student;
import com.niet.gatepass.model.Warden;
import com.niet.gatepass.util.PasswordUtil;

import java.sql.SQLException;

/**
 * Authenticates users across the three login roles: STUDENT, WARDEN, CHIEF_WARDEN.
 * Returns a LoginResult describing success/failure and, on success, the
 * authenticated user object (caller casts based on getRole()).
 */
public class LoginService {

    private final StudentDAO studentDAO = new StudentDAO();
    private final WardenDAO wardenDAO = new WardenDAO();
    private final ChiefWardenDAO chiefWardenDAO = new ChiefWardenDAO();

    public static class LoginResult {
        public boolean success;
        public String message;
        public String role;      // STUDENT / WARDEN / CHIEF_WARDEN
        public Object user;      // Student / Warden / ChiefWarden instance

        public static LoginResult fail(String message) {
            LoginResult r = new LoginResult();
            r.success = false;
            r.message = message;
            return r;
        }

        public static LoginResult ok(String role, Object user) {
            LoginResult r = new LoginResult();
            r.success = true;
            r.role = role;
            r.user = user;
            return r;
        }
    }

    public LoginResult authenticateStudent(String email, String password) throws SQLException {
        Student s = studentDAO.getStudentByEmail(email);
        if (s == null) return LoginResult.fail("No account found with this email.");
        if (!PasswordUtil.verify(password, s.getPasswordHash())) return LoginResult.fail("Incorrect password.");
        if ("PENDING".equals(s.getStatus())) return LoginResult.fail("Your registration is still awaiting warden approval.");
        if ("REJECTED".equals(s.getStatus())) return LoginResult.fail("Your registration was rejected. Contact your warden.");
        return LoginResult.ok("STUDENT", s);
    }

    public LoginResult authenticateWarden(String email, String password) throws SQLException {
        Warden w = wardenDAO.getWardenByEmail(email);
        if (w == null) return LoginResult.fail("No warden account found with this email.");
        if (!PasswordUtil.verify(password, w.getPasswordHash())) return LoginResult.fail("Incorrect password.");
        return LoginResult.ok("WARDEN", w);
    }

    public LoginResult authenticateChiefWarden(String email, String password) throws SQLException {
        ChiefWarden cw = chiefWardenDAO.getChiefWardenByEmail(email);
        if (cw == null) return LoginResult.fail("No chief warden account found with this email.");
        if (!PasswordUtil.verify(password, cw.getPasswordHash())) return LoginResult.fail("Incorrect password.");
        return LoginResult.ok("CHIEF_WARDEN", cw);
    }
}
