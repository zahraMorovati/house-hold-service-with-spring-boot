<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>customer account</title>
    <!-- Required meta tags -->
    <meta charset="utf-8" />
    <meta
            name="viewport"
            content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />

    <!-- Bootstrap CSS -->
    <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
            integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn"
            crossorigin="anonymous"
    />
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
                            <p class="card-title">Customer dashbord</p>
                            <div class="row">
                                <div class="col-12">
                                    <div class="table-responsive">
                                        <form:form action="/login" method="post">
                                            <table border="0" cellpadding="5" >
                                                <tr>
                                                    <td>Name: </td>
                                                    <td>${userDto.name}</td>
                                                </tr>
                                                <tr>
                                                    <td>family: </td>
                                                    <td>${userDto.family}</td>
                                                </tr>
                                                <tr>
                                                    <td>balance: </td>
                                                    <td>${userDto.balance}</td>
                                                </tr>
                                            </table>
                                            <br/> <br/>

                                            <p class="text-danger">${errorMaxReachedOrderNumber}</p>
                                            <p class="text-danger">${errorUserNotConfirmed}</p>
                                            <p class="text-danger">${errorBalanceIsNotEnough}</p>
                                            <p class="text-danger">${timeoutError}</p>
                                            <br/><br/>
                                            <h2> customer order list</h2>
                                            <div class="table-responsive">
                                                <table class="table table-striped table-hover w-25">
                                                    <tr>
                                                        <th>order number</th>
                                                        <th>subService</th>
                                                        <th>registrationDate</th>
                                                        <th>specialist</th>
                                                        <th>order state</th>
                                                        <th>comment</th>
                                                        <th>suggestions</th>
                                                        <th>action</th>

                                                    </tr>
                                                    <c:forEach items="${orders}" var="each_one">
                                                        <tr>
                                                            <td>${each_one.orderCode}</td>
                                                            <td>${each_one.subService}</td>
                                                            <td>${each_one.registrationDate}</td>
                                                            <td>${each_one.specialist}</td>
                                                            <td>${each_one.orderState}</td>
                                                            <td>${each_one.comment}</td>
                                                            <td><a href="/customer/viewSuggestions?orderCode=${each_one.orderCode}<%--&email=${userDto.email}--%>" >view suggestions</a></td>
                                                            <td>
                                                                <a href="/customer/newComment?orderCode=${each_one.orderCode}<%--&email=${userDto.email}--%>">add comment</a>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </table>
                                            </div>
                                            <br/><br/>
                                            <h2> payment </h2>
                                            <div class="table-responsive">
                                                <table class="table table-striped table-hover w-25">
                                                    <tr>
                                                        <th>order number</th>
                                                        <th>subService</th>
                                                        <th>suggestedPrice</th>
                                                        <th>specialist</th>
                                                        <th>order state</th>
                                                        <th>payment</th>


                                                    </tr>
                                                    <c:forEach items="${paymentOrders}" var="each_one">
                                                        <tr>
                                                            <td>${each_one.orderCode}</td>
                                                            <td>${each_one.subService}</td>
                                                            <td>${each_one.suggestedPrice}</td>
                                                            <td>${each_one.specialist}</td>
                                                            <td>${each_one.orderState}</td>
                                                            <td>
                                                            <th><a href="/customer/paymentByBalance?orderCode=${each_one.orderCode}<%--&email=${userDto.email}--%>">pay by balance</a></th>
                                                            <th>
                                                                <a href="/customer/paymentByCard?orderCode=${each_one.orderCode}<%--&email=${userDto.email}--%>">pay by card </a>
                                                            </th>
                                                            </td>

                                                        </tr>
                                                    </c:forEach>
                                                </table>
                                            </div>
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
        src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"
></script>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-fQybjgWLrvvRgtW6bFlB7jaZrFsaBXjsOMm/tB9LTS58ONXgqbR9W8oWht/amnpF"
        crossorigin="anonymous"
></script>
</body>
</html>
