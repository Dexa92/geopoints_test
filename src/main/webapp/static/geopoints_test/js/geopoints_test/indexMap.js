
var map = L.map('index-map');
// create the tile layer with correct attribution
var osmUrl='http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
var osmAttrib='Map data © <a href="http://openstreetmap.org">OpenStreetMap</a> contributors';
var osm = new L.TileLayer(osmUrl, {minZoom: 1, maxZoom: 20, attribution: osmAttrib});

// start the map in South-East England
map.setView(new L.LatLng(51.3, 0.7),9);
map.addLayer(osm);
/*L.Routing.control({
 waypoints: [
 L.latLng(57.74, 11.94),
 L.latLng(57.6792, 11.949),
 L.latLng(57.65, 11.888)
 ]
 }).addTo(map);*/