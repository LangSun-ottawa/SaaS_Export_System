<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../../base.jsp"%>
<!DOCTYPE html>
<html>

<head>
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>AdminLTE2 - To Export</title>
    <meta name="description" content="AdminLTE2定制版">
    <meta name="keywords" content="AdminLTE2定制版">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=your key"
            defer
    ></script>
    <style type="text/css">
        /* Always set the map height explicitly to define the size of the div
         * element that contains the map. */
        #map {
            height: 100%;
        }

        #floating-panel {
            position: absolute;
            top: 10px;
            left: 25%;
            z-index: 5;
            background-color: #fff;
            padding: 5px;
            border: 1px solid #999;
            text-align: center;
            font-family: "Roboto", "sans-serif";
            line-height: 30px;
            padding-left: 10px;
        }
        /* Optional: Makes the sample page fill the window. */
        html,
        body {
            height: 100%;
            margin: 0;
            padding: 0;
        }

    </style>


</head>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <!-- 内容头部 -->
    <section class="content-header">
        <h1>
            货运管理
            <small>新增出口报运单</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">货运管理</a></li>
            <li class="active">新增出口报运单</li>
        </ol>
    </section>
    <!-- 正文区域 -->
    <section class="content">
        <form action="/cargo/export/edit.do" method="post">
        <div class="panel panel-default">
            <div class="panel-heading">对【${id}】出口报运</div>
            <input type="hidden" name="contractIds" value="${id}">
                <div class="row data-type" style="margin: 0px">
                    <div class="col-md-2 title">信用证号</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="信用证号" name="lcno" value="${export.lcno}"/>
                    </div>

                    <div class="col-md-2 title">收货人及地址</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="收货人及地址" name="consignee" value="${export.consignee}"/>
                    </div>

                    <div class="col-md-2 title">唛头</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="唛头" name="marks" value="${export.marks}"/>
                    </div>

                    <div class="col-md-2 title">装运港</div>
                    <div class="col-md-4 data">
                        <input type="text" name="shipmentPort" id="position1" onchange="changePosition1()" class="form-control" placeholder="装运港" />
                    </div>

                    <div class="col-md-2 title">目的港</div>
                    <div class="col-md-4 data">
                        <input type="text" name="destinationPort" id="position2"  onchange="changePosition2()" class="form-control" placeholder="目的港"/>
                    </div>

                    <div class="col-md-2 title">运输方式</div>
                    <div class="col-md-4 data">
<%--                        <input type="text" name="transportMode" class="form-control" placeholder="运输方式" value="${export.transportMode}">--%>
                        <select id="mode" placeholder="运输方式" name="transportMode" class="form-control">
                            <option value="DRIVING">Driving</option>
                            <option value="WALKING">Walking</option>
                            <option value="BICYCLING">Bicycling</option>
                            <option value="TRANSIT">Transit</option>
                        </select>

                    </div>

                    <div class="col-md-2 title">价格条件</div>
                    <div class="col-md-4 data">
                        <select name="priceCondition" class="form-control" placeholder="价格条件" value="${export.priceCondition}">
                            <option value="FBO">FBO</option>
                            <option value="CIF">CIF</option>
                        </select>
                    </div>

                    <div class="col-md-2 title">总箱数</div>
                    <div class="col-md-4 data">
                        <input type="text" name="boxNums" class="form-control" placeholder="总箱数" value="${export.boxNums}"/>
                    </div>

                    <div class="col-md-2 title">总毛重</div>
                    <div class="col-md-4 data">
                        <input type="text" name="grossWeights" class="form-control" placeholder="总毛重"  value="${export.grossWeights}">

                    </div>
                    <div class="col-md-2 title">体积</div>
                    <div class="col-md-4 data">
                        <input type="text" name="measurements" class="form-control" placeholder="体积" value="${export.measurements}">
                    </div>

                    <div class="col-md-2 title rowHeight2x">备注</div>
                    <div class="col-md-10 data rowHeight2x">
                        <textarea class="form-control" rows="3" name="remark">${export.remark}</textarea>
                    </div>
                </div>
            </div>

            <div id="map" style="height: 500px"></div>

            <!--工具栏-->
            <div class="box-tools text-center">
                <button type="submit"  class="btn bg-maroon">保存</button>
                <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
            </div>

        </form>
        <!--工具栏/-->
    </section>

</div>
<script>
    var changePosition1 = function changePosition1() {
        var value1 = document.getElementById('position1').value;
        console.log(value1);
    };

    var changePosition2 = function changePosition2() {
        var value2 = document.getElementById('position2').value;
        console.log(value2);
    };
    function initMap() {
        var directionsService = new google.maps.DirectionsService();
        var directionsRenderer = new google.maps.DirectionsRenderer();
        var map = new google.maps.Map(document.getElementById("map"), {
            zoom: 7,
            center: {
                lat: 41.85,
                lng: -87.65
            }
        });
        directionsRenderer.setMap(map); // =======================

        document.getElementById("mode").addEventListener("change", function () {
            calculateAndDisplayRoute(directionsService, directionsRenderer);
        });
        function calculateAndDisplayRoute(directionsService, directionsRenderer) {
            var selectedMode = document.getElementById("mode").value;
            directionsService.route({
                origin: {
                    query: document.getElementById("position1").value
                },
                destination: {
                    query: document.getElementById("position2").value
                },
                travelMode: google.maps.TravelMode[selectedMode]
            }, function (response, status) {
                if (status == "OK") {
                    directionsRenderer.setDirections(response);
                } else {
                    window.alert("Directions request failed due to " + status);
                }
            });
        } // ================


        var onChangeHandler = function onChangeHandler() {
            calculateAndDisplayRoute(directionsService, directionsRenderer);
        };

        document.getElementById("position1").addEventListener("change", onChangeHandler);
        document.getElementById("position2").addEventListener("change", onChangeHandler);

        var startPlace = document.getElementById("position1");
        var searchBox1 = new google.maps.places.SearchBox(startPlace);
        console.log(searchBox1); // map.controls[google.maps.ControlPosition.TOP_LEFT].push(startPlace); // Bias the SearchBox results towards current map's viewport.

        map.addListener("bounds_changed", function () {
            searchBox1.setBounds(map.getBounds());
        });
        var markers = []; // Listen for the event fired when the user selects a prediction and retrieve
        // more details for that place.

        searchBox1.addListener("places_changed", function () {
            var places = searchBox1.getPlaces();

            if (places.length == 0) {
                return;
            } // Clear out the old markers.


            markers.forEach(function (marker) {
                marker.setMap(null);
            });
            markers = []; // For each place, get the icon, name and location.

            var bounds = new google.maps.LatLngBounds();
            places.forEach(function (place) {
                if (!place.geometry) {
                    console.log("Returned place contains no geometry");
                    return;
                }

                var icon = {
                    url: place.icon,
                    size: new google.maps.Size(71, 71),
                    origin: new google.maps.Point(0, 0),
                    anchor: new google.maps.Point(17, 34),
                    scaledSize: new google.maps.Size(25, 25)
                }; // Create a marker for each place.

                markers.push(new google.maps.Marker({
                    map: map,
                    icon: icon,
                    title: place.name,
                    position: place.geometry.location
                }));

                if (place.geometry.viewport) {
                    // Only geocodes have viewport.
                    bounds.union(place.geometry.viewport);
                } else {
                    bounds.extend(place.geometry.location);
                }
            });
            map.fitBounds(bounds);
        }); // =================
        // +=========

        var desPlace = document.getElementById("position2");
        var searchBox2 = new google.maps.places.SearchBox(desPlace);
        console.log(searchBox2);
        map.addListener("bounds_changed", function () {
            searchBox2.setBounds(map.getBounds());
        });
        var markers2 = []; // Listen for the event fired when the user selects a prediction and retrieve
        // more details for that place.

        searchBox2.addListener("places_changed", function () {
            var places = searchBox2.getPlaces();

            if (places.length == 0) {
                return;
            } // Clear out the old markers2.


            markers2.forEach(function (marker) {
                marker.setMap(null);
            });
            markers2 = []; // For each place, get the icon, name and location.

            var bounds = new google.maps.LatLngBounds();
            places.forEach(function (place) {
                if (!place.geometry) {
                    console.log("Returned place contains no geometry");
                    return;
                }

                var icon = {
                    url: place.icon,
                    size: new google.maps.Size(71, 71),
                    origin: new google.maps.Point(0, 0),
                    anchor: new google.maps.Point(17, 34),
                    scaledSize: new google.maps.Size(25, 25)
                }; // Create a marker for each place.

                markers2.push(new google.maps.Marker({
                    map: map,
                    icon: icon,
                    title: place.name,
                    position: place.geometry.location
                }));

                if (place.geometry.viewport) {
                    // Only geocodes have viewport.
                    bounds.union(place.geometry.viewport);
                } else {
                    bounds.extend(place.geometry.location);
                }
            });
            map.fitBounds(bounds);
        }); // =================
    }

    function calculateAndDisplayRoute(directionsService, directionsRenderer) {
        directionsService.route({
            origin: {
                query: document.getElementById("position1").value
            },
            destination: {
                query: document.getElementById("position2").value
            },
            travelMode: google.maps.TravelMode.DRIVING
        }, function (response, status) {
            if (status === "OK") {
                directionsRenderer.setDirections(response);
            } else {
                window.alert("Directions request failed due to " + status);
            }
        });
    } // let value1,value2;



</script>
</body>

</html>