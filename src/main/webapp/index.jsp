<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Library Management System | Explore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
    <nav class="navbar navbar-expand-lg glass-navbar">
        <div class="container">
            <a class="navbar-brand fw-bold text-white fs-3" href="#">Library</a>
            <div class="ms-auto">
                <% if (session.getAttribute("user")==null) { %>
                    <a href="${pageContext.request.contextPath}/auth/login"
                        class="text-white text-decoration-none me-4 fw-600">Login</a>
                    <a href="${pageContext.request.contextPath}/auth/register" class="btn btn-primary-gradient">Sign
                        Up</a>
                    <% } else { %>
                        <span class="text-white-50 me-3">Welcome, <span
                                class="text-white fw-bold">${user.name}</span></span>
                        <a href="${pageContext.request.contextPath}/auth/logout"
                            class="btn btn-outline-danger btn-sm rounded-pill px-3">Logout</a>
                        <% } %>
            </div>
        </div>
    </nav>

    <main class="container">
        <section class="hero-section animate-fade-in">
            <h1 class="hero-title">Experience Knowledge<br>Like Never Before</h1>
            <p class="hero-subtitle text-white">Access thousands of books, manage your loans, and join a community of
                learners
                in one beautiful space.</p>
        </section>

        <div class="row g-4 mb-5">
            <div class="col-md-4">
                <div class="card glass-card h-100 p-4 border-0">
                    <div class="card-body text-center">
                        <h3 class="card-title h4 fw-bold mb-3 text-white">Browse Books</h3>
                        <p class="text-white-50 mb-4">Explore our vast collection of titles across various genres.</p>
                        <% if (session.getAttribute("user") !=null) { %>
                            <a href="${pageContext.request.contextPath}/books/search?q="
                                class="btn btn-primary-gradient w-100">View Collection</a>
                            <% } else { %>
                                <a href="${pageContext.request.contextPath}/auth/login"
                                    class="btn btn-primary-gradient w-100">View Collection</a>
                                <% } %>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card glass-card h-100 p-4 border-0">
                    <div class="card-body text-center">
                        <h3 class="card-title h4 fw-bold mb-3 text-white">Member Access</h3>
                        <p class="text-white-50 mb-4">Manage your borrowings and reservations easily.</p>
                        <% if (session.getAttribute("user") !=null) { %>
                            <a href="${pageContext.request.contextPath}/user/dashboard"
                                class="btn btn-primary-gradient w-100">Member Area</a>
                            <% } else { %>
                                <a href="${pageContext.request.contextPath}/auth/login"
                                    class="btn btn-primary-gradient w-100">Member Area</a>
                                <% } %>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card glass-card h-100 p-4 border-0">
                    <div class="card-body text-center">
                        <h3 class="card-title h4 fw-bold mb-3 text-white">About Us</h3>
                        <p class="text-white-50 mb-4">Learn more about our library and community programs.</p>
                        <a href="${pageContext.request.contextPath}/about.jsp"
                            class="btn btn-primary-gradient w-100">Learn More</a>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <footer class="py-5 text-center text-white-50 border-top border-secondary mt-5">
        <p>&copy; 2025 Library Management System. Built with passion for readers.</p>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>