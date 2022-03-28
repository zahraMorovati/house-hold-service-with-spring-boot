
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>list specialists</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- plugins:css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/component/tamplate/vendors/feather/feather.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/component/tamplate/vendors/ti-icons/css/themify-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/component/tamplate/vendors/css/vendor.bundle.base.css">
    <!-- endinject -->
    <!-- Plugin css for this page -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/component/tamplate/vendors/datatables.net-bs4/dataTables.bootstrap4.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/component/tamplate/vendors/ti-icons/css/themify-icons.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/component/tamplate/js/select.dataTables.min.css">
    <!-- End plugin css for this page -->
    <!-- inject:css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/component/tamplate/css/vertical-layout-light/style.css">
    <!--end inject-->
</head>
<body>

<%--<div align="center">
    <h2>list specialists</h2>
    <p style="color: #21ea21">${success}</p>

<form action="/manager/listSpecialist" method="get">
    <div class="row">
        <div class="col-3">
            <p>name:</p>
            <input type="text" name="name" class="form-control"/>
        </div>
        <div class="col-3">
            <p>family:</p>
            <input type="text" name="family" class="form-control" />
        </div>
        <div class="col-3">
            <p>email:</p>
            <input type="text" name="email" class="form-control"/>

        </div>
        <div class="col-3 mt-4">
            <input type="submit" value="Search" class="btn btn-primary "/>
        </div>
    </div>
    <br/><br/>
    <table class="table table-striped table-hover">
        <tr>
            <th>Name</th>
            <th>family</th>
            <th>email</th>
            <th>state</th>
            <th>registrationDate</th>
            <th>confirmation</th>

        </tr>
        <c:forEach items="${listSpecialists}" var="each_one">
            <tr>
                <td>${each_one.name}</td>
                <td>${each_one.family}</td>
                <td>${each_one.email}</td>
                <td>${each_one.state}</td>
                <td>${each_one.registrationDate}</td>
                <td><a href="/manager/confirmSpecialist?s=${each_one.email}&lt;%&ndash;&m=${managerEmail}&ndash;%&gt;">confirm user</a></td>
            </tr>
        </c:forEach>
    </table>
</form>
</div>--%>

<div class="container-fluid page-body-wrapper">
    <!-- dashbord -->
    <nav class="sidebar sidebar-offcanvas" id="sidebar">
        <ul class="nav">
            <li class="nav-item" style="background-color: #4747A1; border-radius: 8px">
                <a class="nav-link" href="/manager/dashbord">
                    <i class="icon-grid menu-icon" style="color: #FFFFFF" ></i>
                    <span class="menu-title" style="color:#FFFFFF;">Dashboard</span>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="collapse" href="#ui-basic" aria-expanded="false" aria-controls="ui-basic">
                    <i class="icon-head menu-icon"></i>
                    <span class="menu-title">confirm users</span>
                    <i class="menu-arrow"></i>
                </a>
                <div class="collapse" id="ui-basic">
                    <ul class="nav flex-column sub-menu">
                        <li class="nav-item"> <a class="nav-link" href="/manager/listCustomer?name=&family=&email=">confrim customers</a></li>
                        <li class="nav-item"> <a class="nav-link" href="/manager/listSpecialist?&name=&family=&email=">confrim specialists</a></li>
                    </ul>
                </div>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="collapse" href="#form-elements" aria-expanded="false" aria-controls="form-elements">
                    <i class="icon-columns menu-icon"></i>
                    <span class="menu-title">services</span>
                    <i class="menu-arrow"></i>
                </a>
                <div class="collapse" id="form-elements">
                    <ul class="nav flex-column sub-menu">
                        <li class="nav-item"><a class="nav-link" href="/manager/newService">add service</a></li>
                        <li class="nav-item"><a class="nav-link" href="/manager/newSubService">add subservice</a></li>
                        <li class="nav-item"><a class="nav-link" href="/manager/save-specialist-to-subService">add specialist to <br/> subservice</a></li>
                    </ul>
                </div>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="collapse" href="#tables" aria-expanded="false" aria-controls="tables">
                    <i class="icon-grid-2 menu-icon"></i>
                    <span class="menu-title">advanced filter</span>
                    <i class="menu-arrow"></i>
                </a>
                <div class="collapse" id="tables">
                    <ul class="nav flex-column sub-menu">
                        <li class="nav-item"> <a class="nav-link" href="/manager/advanced-filter-specialists">specialists</a></li>
                        <li class="nav-item"> <a class="nav-link" href="/manager/advanced-filter-customers">customers</a></li>
                        <li class="nav-item"> <a class="nav-link" href="/manager/advanced-filter-orders">orders</a></li>
                    </ul>
                </div>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/">
                    <i class="icon-paper menu-icon"></i>
                    <span class="menu-title">logout</span>
                </a>
            </li>
        </ul>
    </nav>
    <%--end dashbord--%>

    <!-- main panel -->
    <div class="main-panel">
        <div class="content-wrapper">
            <div class="row">
                <div class="col-md-12 grid-margin stretch-card">
                    <div class="card">
                        <div class="card-body">
                            <p class="card-title">list specialists</p>
                            <div class="row">
                                <div class="col-12">
                                    <div class="table-responsive">
                                        <p style="color: #21ea21">${success}</p>

                                        <form action="/manager/listSpecialist" method="get">
                                            <div class="row">
                                                <div class="col-3">
                                                    <p>name:</p>
                                                    <input type="text" name="name" class="form-control"/>
                                                </div>
                                                <div class="col-3">
                                                    <p>family:</p>
                                                    <input type="text" name="family" class="form-control" />
                                                </div>
                                                <div class="col-3">
                                                    <p>email:</p>
                                                    <input type="text" name="email" class="form-control"/>

                                                </div>
                                                <div class="col-3 mt-4">
                                                    <input type="submit" value="Search" class="btn btn-primary "/>
                                                </div>
                                            </div>
                                            <br/><br/>
                                            <table class="table table-striped table-hover">
                                                <tr>
                                                    <th>Name</th>
                                                    <th>family</th>
                                                    <th>email</th>
                                                    <th>state</th>
                                                    <th>registrationDate</th>
                                                    <th>confirmation</th>

                                                </tr>
                                                <c:forEach items="${listSpecialists}" var="each_one">
                                                    <tr>
                                                        <td>${each_one.name}</td>
                                                        <td>${each_one.family}</td>
                                                        <td>${each_one.email}</td>
                                                        <td>${each_one.state}</td>
                                                        <td>${each_one.registrationDate}</td>
                                                        <td><a href="/manager/confirmSpecialist?s=${each_one.email}<%--&m=${managerEmail}--%>">confirm user</a></td>
                                                    </tr>
                                                </c:forEach>
                                            </table>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>


                </div>
            </div>
        </div>
        <!-- content-wrapper ends -->
    </div>
    <!-- main-panel ends -->
</div>




<!-- plugins:js -->
<script src="${pageContext.request.contextPath}/component/tamplate/vendors/js/vendor.bundle.base.js"></script>
<!-- endinject -->
<!-- Plugin js for this page -->
<script src="${pageContext.request.contextPath}/component/tamplate/vendors/chart.js/Chart.min.js"></script>
<script src="${pageContext.request.contextPath}/component/tamplate/vendors/datatables.net/jquery.dataTables.js"></script>
<script src="${pageContext.request.contextPath}/component/tamplate/vendors/datatables.net-bs4/dataTables.bootstrap4.js"></script>
<script src="${pageContext.request.contextPath}/component/tamplate/js/dataTables.select.min.js"></script>

<!-- End plugin js for this page -->
<!-- inject:js -->
<script src="${pageContext.request.contextPath}/component/tamplate/js/off-canvas.js"></script>
<script src="${pageContext.request.contextPath}/component/tamplate/js/hoverable-collapse.js"></script>
<script src="${pageContext.request.contextPath}/component/tamplate/js/template.js"></script>
<script src="${pageContext.request.contextPath}/component/tamplate/js/settings.js"></script>
<script src="${pageContext.request.contextPath}/component/tamplate/js/todolist.js"></script>
<!-- endinject -->
<!-- Custom js for this page-->
<script src="${pageContext.request.contextPath}/component/tamplate/js/dashboard.js"></script>
<script src="${pageContext.request.contextPath}/component/tamplate/js/Chart.roundedBarCharts.js"></script>
<!-- End custom js for this page-->
</body>
</html>
