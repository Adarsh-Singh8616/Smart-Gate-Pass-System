# Smart Gate Pass Approval System — NIET Hostel

A complete Java EE (JSP + Servlet + JDBC + MySQL) web application implementing
the architecture and flowcharts from Phase 1: student registration, gate pass
application, two-stage Warden → Chief Warden approval, and gate verification
by Security Guard.

---

## 1. Project Structure

```
GatePassSystem/
├── database/
│   └── schema.sql              → run this first to create the DB + seed data
├── src/com/niet/gatepass/
│   ├── model/                  → Student, Warden, ChiefWarden, Hostel, GatePass, GatePassLog
│   ├── util/                   → DBConnection, PasswordUtil, PassCodeGenerator
│   ├── dao/                    → StudentDAO, WardenDAO, ChiefWardenDAO, HostelDAO,
│   │                              GatePassDAO, GatePassLogDAO
│   ├── service/                → LoginService, StudentService, GatePassService,
│   │                              ApprovalService, VerificationService
│   ├── controller/              → RegisterServlet, LoginServlet, LogoutServlet,
│   │                              StudentServlet, GatePassServlet, WardenServlet,
│   │                              ChiefWardenServlet, VerifyPassServlet
│   └── filter/                 → AuthenticationFilter, AuthorizationFilter, LoggingFilter
└── WebContent/
    ├── index.jsp, hostel.jsp, pricing.jsp, login.jsp, register.jsp, error.jsp
    ├── css/style.css
    ├── student/  (dashboard, apply_gatepass, my_requests, profile)
    ├── warden/   (dashboard)
    ├── chiefwarden/ (dashboard)
    ├── guard/    (verify)
    └── WEB-INF/web.xml
```

All servlets and filters register themselves with `@WebServlet` / `@WebFilter`
annotations — you do **not** need to add them to `web.xml` manually.

---

## 2. Prerequisites

- JDK 17+
- Apache Tomcat 10+ (uses the `jakarta.servlet.*` namespace — if your Tomcat is
  version 9 or older, change every `jakarta.servlet` import to `javax.servlet`
  in the `controller/` and `filter/` packages)
- MySQL 8+ (MySQL Workbench recommended for setup)
- `mysql-connector-j` JAR (MySQL Connector/J) — download separately and place
  it in `WebContent/WEB-INF/lib/`
- Eclipse (Dynamic Web Project) or IntelliJ IDEA (Ultimate, for Java EE)

---

## 3. Database Setup

1. Open MySQL Workbench (or the `mysql` CLI).
2. Run the full script in `database/schema.sql`. It will:
   - Create the `gatepass_db` database and all tables
   - Seed 3 hostels
   - Seed 1 Chief Warden and 3 Wardens (one per hostel)

**Default seed login credentials (password for all is `admin123`):**

| Role | Email |
|---|---|
| Chief Warden | chiefwarden@niet.co.in |
| Warden (Hostel 1) | warden1@niet.co.in |
| Warden (Hostel 2) | warden2@niet.co.in |
| Warden (Hostel 3) | warden3@niet.co.in |

There are no seeded students — register one via the app's **Register** page;
it will show as `PENDING` until its Warden approves it from their dashboard.

The Security Guard has no database account in this build — the login page
asks for a shared **Guard Access Code**, hardcoded in `LoginServlet.java` as
`NIET-GATE-2026`. Change this constant (or replace it with a real `guards`
table) before real deployment.

---

## 4. Configure the Database Connection

Edit `src/com/niet/gatepass/util/DBConnection.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/gatepass_db?useSSL=false&serverTimezone=UTC";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "root"; // <-- change to your MySQL password
```

---

## 5. Import into Eclipse (Dynamic Web Project)

1. `File → New → Dynamic Web Project` → name it `GatePassSystem`, target
   runtime = your installed Tomcat 10+.
2. Copy `src/` contents into the project's `src` folder (Java sources).
3. Copy `WebContent/` contents into the project's `WebContent` (or
   `src/main/webapp` depending on Eclipse version) folder, keeping `WEB-INF`
   at the same level.
4. Download `mysql-connector-j-<version>.jar` and drop it into
   `WebContent/WEB-INF/lib/`. Add it to the build path if Eclipse doesn't
   pick it up automatically.
5. Right-click the project → `Run As → Run on Server` → select your Tomcat.
6. Browse to `http://localhost:8080/GatePassSystem/`.

## 5b. Import into IntelliJ IDEA (Ultimate)

1. `File → New → Project from Existing Sources`, point at this folder.
2. Mark `src` as **Sources Root**, `WebContent` as the **Web Resource
   Directory** in `Project Structure → Facets → Web`.
3. Add an **Artifact** of type "Web Application: Exploded" including the WEB-INF.
4. Add `mysql-connector-j` as a project library (or drop the jar in
   `WebContent/WEB-INF/lib/`).
5. `Run → Edit Configurations → Add Tomcat Server (Local)`, deploy the artifact.

---

## 6. End-to-End Test Flow

1. **Register** a student from `register.jsp` (status becomes `PENDING`).
2. **Login as a Warden** (e.g. `warden1@niet.co.in` / `admin123`) → approve the
   student registration → student becomes `ACTIVE` and can log in.
3. **Login as the Student** → Apply Gate Pass → note the generated Pass Code
   (e.g. `GP-3F2A9C11`) → status is `PENDING`.
4. **Login as the Warden** again → approve the gate pass request (with
   optional remarks) → status becomes `WARDEN_APPROVED`.
5. **Login as the Chief Warden** (`chiefwarden@niet.co.in` / `admin123`) →
   give final approval → status becomes `ACTIVE` (ready for the gate).
6. **Login as Security Guard** (role = "Security Guard", access code
   `NIET-GATE-2026`) → enter the Pass Code → see student details → click
   **Allow Exit** (status → `EXIT_RECORDED`) → later, verify the same code
   again → click **Record Return** (status → `COMPLETED`).

---

## 7. Status Lifecycle Implemented

```
PENDING → WARDEN_APPROVED → ACTIVE → EXIT_RECORDED → COMPLETED
   |            |
   ▼            ▼
WARDEN_REJECTED   CHIEF_WARDEN_REJECTED
```

Note: the schema also reserves `CHIEF_WARDEN_APPROVED` and `RETURN_RECORDED`
as ENUM values for teams that want a finer-grained lifecycle (e.g. a separate
"approved but not yet active" step, or a distinct return-recorded step before
completion) — the current controller code takes the simplified path shown
above. Extending it is a small change in `ApprovalService` / `VerificationService`.

---

## 8. Security Notes (read before any real deployment)

- Passwords are hashed with SHA-256 (no salt) purely to keep the project
  dependency-free for coursework. For production, use BCrypt/Argon2 with a
  per-user salt.
- The guard access code is a single hardcoded shared secret — replace with a
  real `guards` table + individual accounts for production use.
- Add HTTPS (`useSSL=true` on the JDBC URL, and TLS on Tomcat) before
  deploying outside a local/demo environment.

---

## 9. What's Included vs. Suggested Next Steps

**Included (fully working):**
- Public site (Home / Hostel Info / Pricing)
- Student registration + Warden approval of new accounts
- Login for Student / Warden / Chief Warden / Security Guard
- Gate pass application, two-stage approval, gate verification (exit/return)
- Role-based access control via Filters
- MySQL schema + seed data

**Suggested next steps:**
- Replace the guard shared-code login with a real `guards` table
- Add a Chief Warden "Reports" page (aggregate stats)
- Add real QR-code image generation for the pass code (e.g. ZXing library)
- Add client-side + server-side stronger validation (regex for phone/email)
- Migrate to Maven/Gradle for dependency management instead of manual JARs
