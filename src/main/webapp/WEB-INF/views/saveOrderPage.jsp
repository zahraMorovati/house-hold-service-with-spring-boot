<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
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



<div class="container-fluid page-body-wrapper">
    <!-- dashbord -->
    <nav class="sidebar sidebar-offcanvas" id="sidebar">
        <ul class="nav">
            <li class="nav-item" style="background-color: #4747A1; border-radius: 8px">
                <a class="nav-link" href="/customer/dashbord">
                    <i class="icon-grid menu-icon" style="color: #FFFFFF" ></i>
                    <span class="menu-title" style="color:#FFFFFF;">Dashboard</span>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/customer/addOrder">
                    <i class="icon-paper menu-icon"></i>
                    <span class="menu-title">add order</span>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/">
                    <i class="icon-contract menu-icon"></i>
                    <span class="menu-title">logout</span>
                </a>
            </li>
        </ul>
    </nav>
    <!-- end dashbord -->

    <!-- main panel -->
    <div class="main-panel">
        <div class="content-wrapper">
            <div class="row">
                <div class="col-md-12 grid-margin stretch-card">
                    <div class="card">
                        <div class="card-body">
                            <p class="card-title">save order</p>
                            <div class="row">
                                <div class="col-12">
                                    <div class="table-responsive">
                                        <form:form action="/customer/saveOrder" method="post" modelAttribute="orderDto">
                                            <table border="0" cellpadding="5">
                                                <p class="text-danger">${errorSuggestedPrice}</p>
                                                <p class="text-danger">${saveOrderErrors}</p>
                                                <tr hidden>
                                                    <td>${orderDto.customer}
                                                        <form:hidden path="customer"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>service:</td>
                                                    <td><select id="comboboxService" style="width:200px" class="form-control">
                                                        <c:forEach var="service" items="${services }">
                                                            <option value="${service.value}">${service.value}</option>
                                                        </c:forEach>
                                                    </select></td>
                                                </tr>
                                                <tr>
                                                    <td>sub service:</td>
                                                    <td>
                                                        <form:select path="subService" id="comboboxSubService" cssStyle="width: 200px" cssClass="form-control"></form:select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>suggestedPrice:</td>
                                                    <td><form:input path="suggestedPrice" cssClass="form-control"/></td>
                                                </tr>
                                                <tr>
                                                    <td>explanations:</td>
                                                    <td><form:input path="explanations" cssClass="form-control"/></td>
                                                </tr>
                                                <tr>
                                                    <td>startDate:</td>
                                                    <td><input name="date" type="date" class="form-control"/></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2"> address:</td>
                                                </tr>
                                                <tr>
                                                    <td>city:</td>
                                                    <td><form:input path="city" cssClass="form-control"/></td>
                                                </tr>
                                                <tr>
                                                    <td>city state:</td>
                                                    <td><form:input path="cityState" cssClass="form-control"/></td>
                                                </tr>
                                                <tr>
                                                    <td>plaque:</td>
                                                    <td><form:input path="plaque" cssClass="form-control"/></td>
                                                </tr>
                                                <tr>
                                                    <td>explanations:</td>
                                                    <td><form:input path="addressExplanations" cssClass="form-control"/></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2"><input type="submit" value="Save" class="btn btn-primary"></td>
                                                </tr>
                                            </table>
                                        </form:form>
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
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-fQybjgWLrvvRgtW6bFlB7jaZrFsaBXjsOMm/tB9LTS58ONXgqbR9W8oWht/amnpF"
        crossorigin="anonymous"
></script>
</body>
</html>
