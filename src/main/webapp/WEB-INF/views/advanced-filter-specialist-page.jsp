<%--
  Created by IntelliJ IDEA.
  User: zahra
  Date: 2/16/2022
  Time: 5:17 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>advanced filter specialists</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script>
        jQuery(document).ready(function ($) {

            $("#search-form").submit(function (event) {

                console.log("submitted");
                // Disble the search button
                enableSearchButton(false);

                // Prevent the form from submitting via the browser.
                event.preventDefault();

                searchGetViaAjax();

            });

        });

        function searchViaAjax() {

            var search = {}
            search["name"] = $("#name").val();
            search["family"] = $("#family").val();
            search["email"] = $("#email").val();
            search["startDate"] = $("#startDate").val();
            search["endDate"] = $("#endDate").val();
            search["minOrderNumber"] = $("#minOrderNumber").val();
            search["maxOrderNumber"] = $("#maxOrderNumber").val();

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: window.location.origin + "/rest/advancedFilterSpecialist",
                data: JSON.stringify(search),
                dataType: 'json',
                timeout: 100000,
                success: function (data) {
                    console.log("SUCCESS: ", data);
                    display(data);
                },
                error: function (e) {
                    console.log("ERROR: ", e);
                    display(e);
                },
                done: function (e) {
                    console.log("DONE");
                    enableSearchButton(true);
                }
            });

        }

        function searchGetViaAjax() {

            var name = $("#name").val();
            var family = $("#family").val();
            var email = $("#email").val();
            var startDate = $("#startDate").val();
            var endDate = $("#endDate").val();
            var minOrderNumber = $("#minOrderNumber").val();
            var maxOrderNumber = $("#maxOrderNumber").val();

            $.ajax({
                type: "GET",
                url: window.location.origin + "/rest/advancedFilterSpecialist?name=" + name + "&family=" + family+ "&email=" + email+ "&startDate=" + startDate+ "&endDate=" + endDate+ "&minOrderNumber=" + minOrderNumber+ "&maxOrderNumber=" + maxOrderNumber,
                success: function (data) {
                    console.log("SUCCESS: ", data);
                    display(data);
                },
                error: function (e) {
                    console.log("ERROR: ", e);
                    display(e);
                },
                done: function (e) {
                    console.log("DONE");
                    enableSearchButton(true);
                }
            });

        }

        function enableSearchButton(flag) {
            $("#btn-search").prop("disabled", flag);
        }

        function display(data) {
            console.log(document.referrer)
            var tableHeaderRowCount = 2;
            var table = document.getElementById('specialistTbl');
            var rowCount = table.rows.length;
            for (var i = tableHeaderRowCount; i < rowCount; i++) {
                table.deleteRow(tableHeaderRowCount);
            }
            var trHTML = '';
            $.each(data, function (i, item) {
                console.log(item);
                trHTML += '<tr><td>' + item.name + '</td><td>' + item.family + '</td><td>'+ item.email + '</td><td>' + item.state + '</td><td>'+ item.registrationDate + '</td></tr>';
            });
            $('#specialistTbl').append(trHTML);
        }
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
<%--<div class="container">
    <form id="search-form" class="m-5 p-5 text-center" style="width: 1200px">
        <table id="specialistTbl" class="table table-striped table-hover">
            <tr>
                <td>
                    <label for="name">name: </label><input id="name" name="name" placeHolder="name" type="text"/>
                </td>
                <td>
                    <label for="family">family: </label><input id="family" name="family" placeHolder="family" type="text"/>
                </td>
                <td>
                    <label for="email">email: </label><input id="email" name="email" placeHolder="email" type="email"/>
                </td>
                <td>
                    <p>registration date: </p>
                    <input id="startDate" name="startDate" type="date"/>
                    <br/>
                    <p>between</p>
                    <input id="endDate" name="endDate" type="date"/>
                </td>
                <td>
                    <p>order number: </p>
                    <input id="minOrderNumber" name="minOrderNumber" type="number"/>
                    <br/>
                    <p>between</p>
                    <input id="maxOrderNumber" name="maxOrderNumber" type="number"/>

                </td>
                <td>
                    <button type="submit" id="bth-search">Search</button>
                </td>
            </tr>
            <tr>
                <th>name</th>
                <th>family</th>
                <th>email</th>
                <th>state</th>
                <th>registration date</th>
            </tr>
        </table>
        <div id="feedback"></div>
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
                            <p class="card-title">Advanced filter specialist</p>
                            <div class="row">
                                <div class="col-12">
                                    <div class="table-responsive">
                                        <form id="search-form" class="text-center" style="width: fit-content">
                                            <table id="specialistTbl" class="table table-striped table-hover">
                                                <tr>
                                                    <td>
                                                        <input id="name" name="name" placeHolder="name" type="text"/>
                                                    </td>
                                                    <td>
                                                        <input id="family" name="family" placeHolder="family" type="text"/>
                                                    </td>
                                                    <td>
                                                        <input id="email" name="email" placeHolder="email" type="email"/>
                                                    </td>
                                                    <td>
                                                        <p>registration date: </p>
                                                        <input id="startDate" name="startDate" type="date"/>
                                                        <br/>
                                                        <p>between</p>
                                                        <input id="endDate" name="endDate" type="date"/>
                                                    </td>
                                                    <td>
                                                        <p>order number: </p>
                                                        <input id="minOrderNumber" name="minOrderNumber" type="number"/>
                                                        <br/>
                                                        <p>between</p>
                                                        <input id="maxOrderNumber" name="maxOrderNumber" type="number"/>

                                                    </td>
                                                    <td>
                                                        <button type="submit" id="bth-search" class="btn btn-primary">Search</button>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>name</th>
                                                    <th>family</th>
                                                    <th>email</th>
                                                    <th>state</th>
                                                    <th>registration date</th>
                                                </tr>
                                            </table>
                                            <div id="feedback"></div>
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
