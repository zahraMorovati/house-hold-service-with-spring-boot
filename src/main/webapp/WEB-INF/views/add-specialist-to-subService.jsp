<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>list subServices</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
            crossorigin="anonymous"
    />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script>

        $(document).ready(function(){

            $('#comboboxService').on('change', function(){
                var service = $(this).val();
                $.ajax({
                    type: 'GET',
                    url: window.location.origin+'/loadService/' + service,
                    success: function(result) {
                        var result = JSON.parse(result);
                        var s = '';
                        for(var i = 0; i < result.length; i++) {
                            s += '<option value="' + result[i].name + '">' + result[i].name + '</option>';
                        }
                        $('#comboboxSubService').html(s);
                    }
                });
            });

                var table = document.getElementById('table');

                for(var i = 1; i < table.rows.length; i++)
                {
                    table.rows[i].onclick = function()
                    {
                        document.getElementById("email").value = this.cells[2].innerHTML;
                    };
                }


        });
    </script>
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
    <p style="color: #ee2222">${duplicatedSubService}</p>
    <p style="color: #ee2222">${errors}</p>
    <p style="color: #2fef2f">${specialistAddedSuccessfully}</p>
    <form:form action="/manager/add-specialist-to-subService">
        <table>
            <tr>
                <td>
                    service:
                </td>
                <td><select id="comboboxService" style="width:200px" class="form-control">
                    <c:forEach var="service" items="${services}">
                        <option value="${service.value}">${service.value}</option>
                    </c:forEach>
                </select></td>
            </tr>
            <tr>
                <td>sub service:</td>
                <td>
                    <select name="subService" id="comboboxSubService" style="width: 200px" class="form-control"></select>
                </td>
            </tr>
            <tr>
                <td>specialist:</td>
                <td>
                    <input name="email" id="email" style="width: 200px" class="form-control">
                </td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="save" style="width: 200px" class="btn btn-primary"></td>
            </tr>

        </table>
    </form:form>
    <br/>
    <table id="table" class="table table-striped table-hover">
        <tr>
            <th>name</th>
            <th>family</th>
            <th>email</th>
            <th>state</th>
            <th>registration date</th>

        </tr>
        <c:forEach items="${specialists}" var="each_one">
            <tr>

                <td>${each_one.name}</td>
                <td>${each_one.family}</td>
                <td>${each_one.email}</td>
                <td>${each_one.state}</td>
                <td>${each_one.registrationDate}</td>
            </tr>
        </c:forEach>
    </table>

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
                            <p class="card-title">add specialist to subservice</p>
                            <div class="row">
                                <div class="col-12">
                                    <div class="table-responsive">
                                        <p style="color: #ee2222">${duplicatedSubService}</p>
                                        <p style="color: #ee2222">${errors}</p>
                                        <p style="color: #2fef2f">${specialistAddedSuccessfully}</p>
                                        <form:form action="/manager/add-specialist-to-subService">
                                            <div class="row p-4">
                                                <div class="col-3">
                                                    <p>service:</p>
                                                    <select id="comboboxService" style="width:200px" class="form-control">
                                                        <c:forEach var="service" items="${services}">
                                                            <option value="${service.value}">${service.value}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="col-3">
                                                    <p>sub service:</p>
                                                    <select name="subService" id="comboboxSubService" style="width: 200px" class="form-control"></select>

                                                </div>
                                                <div class="col-3">
                                                    <p>specialist:</p>
                                                    <input name="email" id="email" style="width: 200px" class="form-control">

                                                </div>
                                                <div class="col-3 mt-4">
                                                    <input type="submit" value="save" style="width: 200px" class="btn btn-primary">
                                                </div>

                                            </div>

                                        </form:form>
                                        <br/>
                                        <table id="table" class="table table-striped table-hover">
                                            <tr>
                                                <th>name</th>
                                                <th>family</th>
                                                <th>email</th>
                                                <th>state</th>
                                                <th>registration date</th>

                                            </tr>
                                            <c:forEach items="${specialists}" var="each_one">
                                                <tr>

                                                    <td>${each_one.name}</td>
                                                    <td>${each_one.family}</td>
                                                    <td>${each_one.email}</td>
                                                    <td>${each_one.state}</td>
                                                    <td>${each_one.registrationDate}</td>
                                                </tr>
                                            </c:forEach>
                                        </table>
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
