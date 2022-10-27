package com.radya.sfa.view.report;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by RadyaLabs PC on 23/08/2017.
 */

@NoArgsConstructor
@Data
public class ModelGoogleDirection {


    /**
     * geocoded_waypoints : [{"geocoder_status":"OK","place_id":"ChIJM4tDn6_naC4RR9f0sS_cYFs","types":["street_address"]},{"geocoder_status":"OK","place_id":"ChIJ3fXZRX_oaC4RiWqhLwfyjZA","types":["street_address"]}]
     * routes : [{"bounds":{"northeast":{"lat":-6.892812999999999,"lng":107.6353289},"southwest":{"lat":-6.9294856,"lng":107.6264431}},"copyrights":"Map data ©2017 Google","legs":[{"distance":{"text":"5.6 km","value":5588},"duration":{"text":"20 mins","value":1176},"end_address":"Jl. Pelajar Pejuang 45 No.22A, Lkr. Sel., Lengkong, Kota Bandung, Jawa Barat 40263, Indonesia","end_location":{"lat":-6.9284932,"lng":107.6266795},"start_address":"Jl. Sido Luhur No.14, Sukaluyu, Cibeunying Kaler, Kota Bandung, Jawa Barat 40123, Indonesia","start_location":{"lat":-6.894158999999999,"lng":107.631945},"steps":[{"distance":{"text":"0.2 km","value":249},"duration":{"text":"2 mins","value":106},"end_location":{"lat":-6.8940237,"lng":107.6341447},"html_instructions":"Head <b>east<\/b> on <b>Jl. Sido Luhur<\/b> toward <b>Jl. Batik Lasem<\/b>","polyline":{"points":"noai@sz|oSUeA[wACoB?CHoANuA"},"start_location":{"lat":-6.894158999999999,"lng":107.631945},"travel_mode":"DRIVING"},{"distance":{"text":"0.1 km","value":139},"duration":{"text":"1 min","value":38},"end_location":{"lat":-6.892812999999999,"lng":107.6344648},"html_instructions":"Turn <b>left<\/b> onto <b>Jl. Batik Kumeli<\/b>","maneuver":"turn-left","polyline":{"points":"rnai@kh}oSmCe@cBY"},"start_location":{"lat":-6.8940237,"lng":107.6341447},"travel_mode":"DRIVING"},{"distance":{"text":"89 m","value":89},"duration":{"text":"1 min","value":23},"end_location":{"lat":-6.8929754,"lng":107.6352574},"html_instructions":"Turn <b>right<\/b> at the 1st cross street onto <b>Jl. Sido Mukti<\/b>","maneuver":"turn-right","polyline":{"points":"`gai@kj}oSJgATwA"},"start_location":{"lat":-6.892812999999999,"lng":107.6344648},"travel_mode":"DRIVING"},{"distance":{"text":"1.0 km","value":1038},"duration":{"text":"4 mins","value":254},"end_location":{"lat":-6.901879699999999,"lng":107.6350904},"html_instructions":"Turn <b>right<\/b> after NDW Nurdhuha Wisata (on the left)<div style=\"font-size:0.9em\">Pass by Nurul Islam Mosque (on the right in 700&nbsp;m)<\/div>","maneuver":"turn-right","polyline":{"points":"bhai@ko}oSPMzQpCr@JvGjApAT`@Db@Dv@BbA?l@Et@MRCn@INExAa@dBs@tAo@d@Sp@]"},"start_location":{"lat":-6.8929754,"lng":107.6352574},"travel_mode":"DRIVING"},{"distance":{"text":"0.7 km","value":725},"duration":{"text":"2 mins","value":117},"end_location":{"lat":-6.906231999999999,"lng":107.6303991},"html_instructions":"At Masjid Jenderal Sudirman, <b>Jl. Pahlawan<\/b> turns <b>right<\/b> and becomes <b>Jl. Brigadir Jend. Katamso<\/b><div style=\"font-size:0.9em\">Pass by Bimbel Cinderella (on the right in 450&nbsp;m)<\/div>","polyline":{"points":"v_ci@in}oSl@nAd@jAf@rAf@lAJRp@vAp@|@rApAr@f@|@r@lBpAxE|CRHn@X"},"start_location":{"lat":-6.901879699999999,"lng":107.6350904},"travel_mode":"DRIVING"},{"distance":{"text":"1.0 km","value":950},"duration":{"text":"2 mins","value":137},"end_location":{"lat":-6.913404600000001,"lng":107.6344128},"html_instructions":"Turn <b>left<\/b> onto <b>Jl. W.R. Supratman<\/b><div style=\"font-size:0.9em\">Pass by mitra insurance (on the left in 900&nbsp;m)<\/div>","maneuver":"turn-left","polyline":{"points":"|zci@_q|oSr@\\t@b@d@OxCcBnBgApC_BBC@ABAp@a@BALI`@W`Aq@ZUVOrDqBtJoF^W|@g@"},"start_location":{"lat":-6.906231999999999,"lng":107.6303991},"travel_mode":"DRIVING"},{"distance":{"text":"0.5 km","value":514},"duration":{"text":"2 mins","value":120},"end_location":{"lat":-6.915610699999999,"lng":107.63033},"html_instructions":"Turn <b>right<\/b> onto <b>Jl. Jend. A. Yani<\/b><div style=\"font-size:0.9em\">Parts of this road are closed 6:00 \u2013 9:00 AM<\/div><div style=\"font-size:0.9em\">Pass by Bandung Permai Hotel (on the left)<\/div>","maneuver":"turn-right","polyline":{"points":"vgei@aj}oSLR`AlCDHNZ\\r@h@lAl@~A~@nBHPFPXj@Vn@PRDD?@Rd@P^Vj@BF"},"start_location":{"lat":-6.913404600000001,"lng":107.6344128},"travel_mode":"DRIVING"},{"distance":{"text":"1.2 km","value":1191},"duration":{"text":"4 mins","value":231},"end_location":{"lat":-6.924496299999999,"lng":107.6277861},"html_instructions":"Turn <b>left<\/b> onto <b>Jl. Laswi<\/b><div style=\"font-size:0.9em\">Pass by the pharmacy (on the left)<\/div>","maneuver":"turn-left","polyline":{"points":"puei@qp|oSVJ`@NlAc@XKTEz@[l@UbBo@XMrCkAJETEP@RBfARdAPbANRB^FbC^dBXr@Pd@FRF\\NLJRNPRLRRd@JXXp@P`@J^LXHVP`@l@jAPRRR\\V\\PLHNHNFr@LbAT"},"start_location":{"lat":-6.915610699999999,"lng":107.63033},"travel_mode":"DRIVING"},{"distance":{"text":"0.6 km","value":572},"duration":{"text":"2 mins","value":109},"end_location":{"lat":-6.9294856,"lng":107.6265366},"html_instructions":"Continue straight onto <b>Jl. Pelajar Pejuang 45<\/b><div style=\"font-size:0.9em\">Pass by Otogard Bandung (on the left in 500&nbsp;m)<\/div>","maneuver":"straight","polyline":{"points":"bmgi@u`|oSNDd@HtAVd@Hh@JlBXp@JpAT|@LrATH@rARdCZB?RDTDf@JjAP"},"start_location":{"lat":-6.924496299999999,"lng":107.6277861},"travel_mode":"DRIVING"},{"distance":{"text":"0.1 km","value":121},"duration":{"text":"1 min","value":41},"end_location":{"lat":-6.9284932,"lng":107.6266795},"html_instructions":"Make a <b>U-turn<\/b> at <b>Jl. Talaga Bodas<\/b><div style=\"font-size:0.9em\">Destination will be on the left<\/div>","maneuver":"uturn-right","polyline":{"points":"hlhi@{x{oSERaEo@"},"start_location":{"lat":-6.9294856,"lng":107.6265366},"travel_mode":"DRIVING"}],"traffic_speed_entry":[],"via_waypoint":[]}],"overview_polyline":{"points":"noai@sz|oSq@}CCsBXeDqF_A`@_DPMzQpCjIvArBZzAHbA?l@EhAQ~@OxAa@dBs@zBcAp@]l@nAlA~Cr@`Bp@vAp@|@rApAr@f@jDdClFfDbBv@t@b@d@OhGkDzCgBdBeA|AgAjEaCtKgG|@g@LRfAvCl@nAvAlDhA`C`@|@Vn@PRDFd@dAZr@x@Z|Bu@fFoB~CqATEP@zAVhC`@|GdAlB`@j@Zd@b@`@x@d@jAt@rB~@lBd@f@z@h@\\RbATrAZjEv@nHhA|AVxEn@tAVjAPERaEo@"},"summary":"Jl. Laswi","warnings":[],"waypoint_order":[]}]
     * status : OK
     */

    private String status;
    private List<GeocodedWaypoints> geocoded_waypoints;
    private List<Routes> routes;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GeocodedWaypoints> getGeocoded_waypoints() {
        return geocoded_waypoints;
    }

    public void setGeocoded_waypoints(List<GeocodedWaypoints> geocoded_waypoints) {
        this.geocoded_waypoints = geocoded_waypoints;
    }

    public List<Routes> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Routes> routes) {
        this.routes = routes;
    }

    @NoArgsConstructor
    @Data
    public static class GeocodedWaypoints {
        /**
         * geocoder_status : OK
         * place_id : ChIJM4tDn6_naC4RR9f0sS_cYFs
         * types : ["street_address"]
         */

        private String geocoder_status;
        private String place_id;
        private List<String> types;

        public String getGeocoder_status() {
            return geocoder_status;
        }

        public void setGeocoder_status(String geocoder_status) {
            this.geocoder_status = geocoder_status;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }
    }

    @NoArgsConstructor
    @Data
    public static class Routes {
        /**
         * bounds : {"northeast":{"lat":-6.892812999999999,"lng":107.6353289},"southwest":{"lat":-6.9294856,"lng":107.6264431}}
         * copyrights : Map data ©2017 Google
         * legs : [{"distance":{"text":"5.6 km","value":5588},"duration":{"text":"20 mins","value":1176},"end_address":"Jl. Pelajar Pejuang 45 No.22A, Lkr. Sel., Lengkong, Kota Bandung, Jawa Barat 40263, Indonesia","end_location":{"lat":-6.9284932,"lng":107.6266795},"start_address":"Jl. Sido Luhur No.14, Sukaluyu, Cibeunying Kaler, Kota Bandung, Jawa Barat 40123, Indonesia","start_location":{"lat":-6.894158999999999,"lng":107.631945},"steps":[{"distance":{"text":"0.2 km","value":249},"duration":{"text":"2 mins","value":106},"end_location":{"lat":-6.8940237,"lng":107.6341447},"html_instructions":"Head <b>east<\/b> on <b>Jl. Sido Luhur<\/b> toward <b>Jl. Batik Lasem<\/b>","polyline":{"points":"noai@sz|oSUeA[wACoB?CHoANuA"},"start_location":{"lat":-6.894158999999999,"lng":107.631945},"travel_mode":"DRIVING"},{"distance":{"text":"0.1 km","value":139},"duration":{"text":"1 min","value":38},"end_location":{"lat":-6.892812999999999,"lng":107.6344648},"html_instructions":"Turn <b>left<\/b> onto <b>Jl. Batik Kumeli<\/b>","maneuver":"turn-left","polyline":{"points":"rnai@kh}oSmCe@cBY"},"start_location":{"lat":-6.8940237,"lng":107.6341447},"travel_mode":"DRIVING"},{"distance":{"text":"89 m","value":89},"duration":{"text":"1 min","value":23},"end_location":{"lat":-6.8929754,"lng":107.6352574},"html_instructions":"Turn <b>right<\/b> at the 1st cross street onto <b>Jl. Sido Mukti<\/b>","maneuver":"turn-right","polyline":{"points":"`gai@kj}oSJgATwA"},"start_location":{"lat":-6.892812999999999,"lng":107.6344648},"travel_mode":"DRIVING"},{"distance":{"text":"1.0 km","value":1038},"duration":{"text":"4 mins","value":254},"end_location":{"lat":-6.901879699999999,"lng":107.6350904},"html_instructions":"Turn <b>right<\/b> after NDW Nurdhuha Wisata (on the left)<div style=\"font-size:0.9em\">Pass by Nurul Islam Mosque (on the right in 700&nbsp;m)<\/div>","maneuver":"turn-right","polyline":{"points":"bhai@ko}oSPMzQpCr@JvGjApAT`@Db@Dv@BbA?l@Et@MRCn@INExAa@dBs@tAo@d@Sp@]"},"start_location":{"lat":-6.8929754,"lng":107.6352574},"travel_mode":"DRIVING"},{"distance":{"text":"0.7 km","value":725},"duration":{"text":"2 mins","value":117},"end_location":{"lat":-6.906231999999999,"lng":107.6303991},"html_instructions":"At Masjid Jenderal Sudirman, <b>Jl. Pahlawan<\/b> turns <b>right<\/b> and becomes <b>Jl. Brigadir Jend. Katamso<\/b><div style=\"font-size:0.9em\">Pass by Bimbel Cinderella (on the right in 450&nbsp;m)<\/div>","polyline":{"points":"v_ci@in}oSl@nAd@jAf@rAf@lAJRp@vAp@|@rApAr@f@|@r@lBpAxE|CRHn@X"},"start_location":{"lat":-6.901879699999999,"lng":107.6350904},"travel_mode":"DRIVING"},{"distance":{"text":"1.0 km","value":950},"duration":{"text":"2 mins","value":137},"end_location":{"lat":-6.913404600000001,"lng":107.6344128},"html_instructions":"Turn <b>left<\/b> onto <b>Jl. W.R. Supratman<\/b><div style=\"font-size:0.9em\">Pass by mitra insurance (on the left in 900&nbsp;m)<\/div>","maneuver":"turn-left","polyline":{"points":"|zci@_q|oSr@\\t@b@d@OxCcBnBgApC_BBC@ABAp@a@BALI`@W`Aq@ZUVOrDqBtJoF^W|@g@"},"start_location":{"lat":-6.906231999999999,"lng":107.6303991},"travel_mode":"DRIVING"},{"distance":{"text":"0.5 km","value":514},"duration":{"text":"2 mins","value":120},"end_location":{"lat":-6.915610699999999,"lng":107.63033},"html_instructions":"Turn <b>right<\/b> onto <b>Jl. Jend. A. Yani<\/b><div style=\"font-size:0.9em\">Parts of this road are closed 6:00 \u2013 9:00 AM<\/div><div style=\"font-size:0.9em\">Pass by Bandung Permai Hotel (on the left)<\/div>","maneuver":"turn-right","polyline":{"points":"vgei@aj}oSLR`AlCDHNZ\\r@h@lAl@~A~@nBHPFPXj@Vn@PRDD?@Rd@P^Vj@BF"},"start_location":{"lat":-6.913404600000001,"lng":107.6344128},"travel_mode":"DRIVING"},{"distance":{"text":"1.2 km","value":1191},"duration":{"text":"4 mins","value":231},"end_location":{"lat":-6.924496299999999,"lng":107.6277861},"html_instructions":"Turn <b>left<\/b> onto <b>Jl. Laswi<\/b><div style=\"font-size:0.9em\">Pass by the pharmacy (on the left)<\/div>","maneuver":"turn-left","polyline":{"points":"puei@qp|oSVJ`@NlAc@XKTEz@[l@UbBo@XMrCkAJETEP@RBfARdAPbANRB^FbC^dBXr@Pd@FRF\\NLJRNPRLRRd@JXXp@P`@J^LXHVP`@l@jAPRRR\\V\\PLHNHNFr@LbAT"},"start_location":{"lat":-6.915610699999999,"lng":107.63033},"travel_mode":"DRIVING"},{"distance":{"text":"0.6 km","value":572},"duration":{"text":"2 mins","value":109},"end_location":{"lat":-6.9294856,"lng":107.6265366},"html_instructions":"Continue straight onto <b>Jl. Pelajar Pejuang 45<\/b><div style=\"font-size:0.9em\">Pass by Otogard Bandung (on the left in 500&nbsp;m)<\/div>","maneuver":"straight","polyline":{"points":"bmgi@u`|oSNDd@HtAVd@Hh@JlBXp@JpAT|@LrATH@rARdCZB?RDTDf@JjAP"},"start_location":{"lat":-6.924496299999999,"lng":107.6277861},"travel_mode":"DRIVING"},{"distance":{"text":"0.1 km","value":121},"duration":{"text":"1 min","value":41},"end_location":{"lat":-6.9284932,"lng":107.6266795},"html_instructions":"Make a <b>U-turn<\/b> at <b>Jl. Talaga Bodas<\/b><div style=\"font-size:0.9em\">Destination will be on the left<\/div>","maneuver":"uturn-right","polyline":{"points":"hlhi@{x{oSERaEo@"},"start_location":{"lat":-6.9294856,"lng":107.6265366},"travel_mode":"DRIVING"}],"traffic_speed_entry":[],"via_waypoint":[]}]
         * overview_polyline : {"points":"noai@sz|oSq@}CCsBXeDqF_A`@_DPMzQpCjIvArBZzAHbA?l@EhAQ~@OxAa@dBs@zBcAp@]l@nAlA~Cr@`Bp@vAp@|@rApAr@f@jDdClFfDbBv@t@b@d@OhGkDzCgBdBeA|AgAjEaCtKgG|@g@LRfAvCl@nAvAlDhA`C`@|@Vn@PRDFd@dAZr@x@Z|Bu@fFoB~CqATEP@zAVhC`@|GdAlB`@j@Zd@b@`@x@d@jAt@rB~@lBd@f@z@h@\\RbATrAZjEv@nHhA|AVxEn@tAVjAPERaEo@"}
         * summary : Jl. Laswi
         * warnings : []
         * waypoint_order : []
         */

        private Bounds bounds;
        private String copyrights;
        private OverviewPolyline overview_polyline;
        private String summary;
        private List<Legs> legs;
        private List<?> warnings;
        private List<?> waypoint_order;

        public Bounds getBounds() {
            return bounds;
        }

        public void setBounds(Bounds bounds) {
            this.bounds = bounds;
        }

        public String getCopyrights() {
            return copyrights;
        }

        public void setCopyrights(String copyrights) {
            this.copyrights = copyrights;
        }

        public OverviewPolyline getOverview_polyline() {
            return overview_polyline;
        }

        public void setOverview_polyline(OverviewPolyline overview_polyline) {
            this.overview_polyline = overview_polyline;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public List<Legs> getLegs() {
            return legs;
        }

        public void setLegs(List<Legs> legs) {
            this.legs = legs;
        }

        public List<?> getWarnings() {
            return warnings;
        }

        public void setWarnings(List<?> warnings) {
            this.warnings = warnings;
        }

        public List<?> getWaypoint_order() {
            return waypoint_order;
        }

        public void setWaypoint_order(List<?> waypoint_order) {
            this.waypoint_order = waypoint_order;
        }

        @NoArgsConstructor
        @Data
        public static class Bounds {
            /**
             * northeast : {"lat":-6.892812999999999,"lng":107.6353289}
             * southwest : {"lat":-6.9294856,"lng":107.6264431}
             */

            private Northeast northeast;
            private Southwest southwest;

            public Northeast getNortheast() {
                return northeast;
            }

            public void setNortheast(Northeast northeast) {
                this.northeast = northeast;
            }

            public Southwest getSouthwest() {
                return southwest;
            }

            public void setSouthwest(Southwest southwest) {
                this.southwest = southwest;
            }

            @NoArgsConstructor
            @Data
            public static class Northeast {
                /**
                 * lat : -6.892812999999999
                 * lng : 107.6353289
                 */

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }

            @NoArgsConstructor
            @Data
            public static class Southwest {
                /**
                 * lat : -6.9294856
                 * lng : 107.6264431
                 */

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }
        }

        @NoArgsConstructor
        @Data
        public static class OverviewPolyline {
            /**
             * points : noai@sz|oSq@}CCsBXeDqF_A`@_DPMzQpCjIvArBZzAHbA?l@EhAQ~@OxAa@dBs@zBcAp@]l@nAlA~Cr@`Bp@vAp@|@rApAr@f@jDdClFfDbBv@t@b@d@OhGkDzCgBdBeA|AgAjEaCtKgG|@g@LRfAvCl@nAvAlDhA`C`@|@Vn@PRDFd@dAZr@x@Z|Bu@fFoB~CqATEP@zAVhC`@|GdAlB`@j@Zd@b@`@x@d@jAt@rB~@lBd@f@z@h@\RbATrAZjEv@nHhA|AVxEn@tAVjAPERaEo@
             */

            private String points;

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }
        }

        @NoArgsConstructor
        @Data
        public static class Legs {
            /**
             * distance : {"text":"5.6 km","value":5588}
             * duration : {"text":"20 mins","value":1176}
             * end_address : Jl. Pelajar Pejuang 45 No.22A, Lkr. Sel., Lengkong, Kota Bandung, Jawa Barat 40263, Indonesia
             * end_location : {"lat":-6.9284932,"lng":107.6266795}
             * start_address : Jl. Sido Luhur No.14, Sukaluyu, Cibeunying Kaler, Kota Bandung, Jawa Barat 40123, Indonesia
             * start_location : {"lat":-6.894158999999999,"lng":107.631945}
             * steps : [{"distance":{"text":"0.2 km","value":249},"duration":{"text":"2 mins","value":106},"end_location":{"lat":-6.8940237,"lng":107.6341447},"html_instructions":"Head <b>east<\/b> on <b>Jl. Sido Luhur<\/b> toward <b>Jl. Batik Lasem<\/b>","polyline":{"points":"noai@sz|oSUeA[wACoB?CHoANuA"},"start_location":{"lat":-6.894158999999999,"lng":107.631945},"travel_mode":"DRIVING"},{"distance":{"text":"0.1 km","value":139},"duration":{"text":"1 min","value":38},"end_location":{"lat":-6.892812999999999,"lng":107.6344648},"html_instructions":"Turn <b>left<\/b> onto <b>Jl. Batik Kumeli<\/b>","maneuver":"turn-left","polyline":{"points":"rnai@kh}oSmCe@cBY"},"start_location":{"lat":-6.8940237,"lng":107.6341447},"travel_mode":"DRIVING"},{"distance":{"text":"89 m","value":89},"duration":{"text":"1 min","value":23},"end_location":{"lat":-6.8929754,"lng":107.6352574},"html_instructions":"Turn <b>right<\/b> at the 1st cross street onto <b>Jl. Sido Mukti<\/b>","maneuver":"turn-right","polyline":{"points":"`gai@kj}oSJgATwA"},"start_location":{"lat":-6.892812999999999,"lng":107.6344648},"travel_mode":"DRIVING"},{"distance":{"text":"1.0 km","value":1038},"duration":{"text":"4 mins","value":254},"end_location":{"lat":-6.901879699999999,"lng":107.6350904},"html_instructions":"Turn <b>right<\/b> after NDW Nurdhuha Wisata (on the left)<div style=\"font-size:0.9em\">Pass by Nurul Islam Mosque (on the right in 700&nbsp;m)<\/div>","maneuver":"turn-right","polyline":{"points":"bhai@ko}oSPMzQpCr@JvGjApAT`@Db@Dv@BbA?l@Et@MRCn@INExAa@dBs@tAo@d@Sp@]"},"start_location":{"lat":-6.8929754,"lng":107.6352574},"travel_mode":"DRIVING"},{"distance":{"text":"0.7 km","value":725},"duration":{"text":"2 mins","value":117},"end_location":{"lat":-6.906231999999999,"lng":107.6303991},"html_instructions":"At Masjid Jenderal Sudirman, <b>Jl. Pahlawan<\/b> turns <b>right<\/b> and becomes <b>Jl. Brigadir Jend. Katamso<\/b><div style=\"font-size:0.9em\">Pass by Bimbel Cinderella (on the right in 450&nbsp;m)<\/div>","polyline":{"points":"v_ci@in}oSl@nAd@jAf@rAf@lAJRp@vAp@|@rApAr@f@|@r@lBpAxE|CRHn@X"},"start_location":{"lat":-6.901879699999999,"lng":107.6350904},"travel_mode":"DRIVING"},{"distance":{"text":"1.0 km","value":950},"duration":{"text":"2 mins","value":137},"end_location":{"lat":-6.913404600000001,"lng":107.6344128},"html_instructions":"Turn <b>left<\/b> onto <b>Jl. W.R. Supratman<\/b><div style=\"font-size:0.9em\">Pass by mitra insurance (on the left in 900&nbsp;m)<\/div>","maneuver":"turn-left","polyline":{"points":"|zci@_q|oSr@\\t@b@d@OxCcBnBgApC_BBC@ABAp@a@BALI`@W`Aq@ZUVOrDqBtJoF^W|@g@"},"start_location":{"lat":-6.906231999999999,"lng":107.6303991},"travel_mode":"DRIVING"},{"distance":{"text":"0.5 km","value":514},"duration":{"text":"2 mins","value":120},"end_location":{"lat":-6.915610699999999,"lng":107.63033},"html_instructions":"Turn <b>right<\/b> onto <b>Jl. Jend. A. Yani<\/b><div style=\"font-size:0.9em\">Parts of this road are closed 6:00 \u2013 9:00 AM<\/div><div style=\"font-size:0.9em\">Pass by Bandung Permai Hotel (on the left)<\/div>","maneuver":"turn-right","polyline":{"points":"vgei@aj}oSLR`AlCDHNZ\\r@h@lAl@~A~@nBHPFPXj@Vn@PRDD?@Rd@P^Vj@BF"},"start_location":{"lat":-6.913404600000001,"lng":107.6344128},"travel_mode":"DRIVING"},{"distance":{"text":"1.2 km","value":1191},"duration":{"text":"4 mins","value":231},"end_location":{"lat":-6.924496299999999,"lng":107.6277861},"html_instructions":"Turn <b>left<\/b> onto <b>Jl. Laswi<\/b><div style=\"font-size:0.9em\">Pass by the pharmacy (on the left)<\/div>","maneuver":"turn-left","polyline":{"points":"puei@qp|oSVJ`@NlAc@XKTEz@[l@UbBo@XMrCkAJETEP@RBfARdAPbANRB^FbC^dBXr@Pd@FRF\\NLJRNPRLRRd@JXXp@P`@J^LXHVP`@l@jAPRRR\\V\\PLHNHNFr@LbAT"},"start_location":{"lat":-6.915610699999999,"lng":107.63033},"travel_mode":"DRIVING"},{"distance":{"text":"0.6 km","value":572},"duration":{"text":"2 mins","value":109},"end_location":{"lat":-6.9294856,"lng":107.6265366},"html_instructions":"Continue straight onto <b>Jl. Pelajar Pejuang 45<\/b><div style=\"font-size:0.9em\">Pass by Otogard Bandung (on the left in 500&nbsp;m)<\/div>","maneuver":"straight","polyline":{"points":"bmgi@u`|oSNDd@HtAVd@Hh@JlBXp@JpAT|@LrATH@rARdCZB?RDTDf@JjAP"},"start_location":{"lat":-6.924496299999999,"lng":107.6277861},"travel_mode":"DRIVING"},{"distance":{"text":"0.1 km","value":121},"duration":{"text":"1 min","value":41},"end_location":{"lat":-6.9284932,"lng":107.6266795},"html_instructions":"Make a <b>U-turn<\/b> at <b>Jl. Talaga Bodas<\/b><div style=\"font-size:0.9em\">Destination will be on the left<\/div>","maneuver":"uturn-right","polyline":{"points":"hlhi@{x{oSERaEo@"},"start_location":{"lat":-6.9294856,"lng":107.6265366},"travel_mode":"DRIVING"}]
             * traffic_speed_entry : []
             * via_waypoint : []
             */

            private Distance distance;
            private Duration duration;
            private String end_address;
            private EndLocation end_location;
            private String start_address;
            private StartLocation start_location;
            private List<Steps> steps;
            private List<?> traffic_speed_entry;
            private List<?> via_waypoint;

            public Distance getDistance() {
                return distance;
            }

            public void setDistance(Distance distance) {
                this.distance = distance;
            }

            public Duration getDuration() {
                return duration;
            }

            public void setDuration(Duration duration) {
                this.duration = duration;
            }

            public String getEnd_address() {
                return end_address;
            }

            public void setEnd_address(String end_address) {
                this.end_address = end_address;
            }

            public EndLocation getEnd_location() {
                return end_location;
            }

            public void setEnd_location(EndLocation end_location) {
                this.end_location = end_location;
            }

            public String getStart_address() {
                return start_address;
            }

            public void setStart_address(String start_address) {
                this.start_address = start_address;
            }

            public StartLocation getStart_location() {
                return start_location;
            }

            public void setStart_location(StartLocation start_location) {
                this.start_location = start_location;
            }

            public List<Steps> getSteps() {
                return steps;
            }

            public void setSteps(List<Steps> steps) {
                this.steps = steps;
            }

            public List<?> getTraffic_speed_entry() {
                return traffic_speed_entry;
            }

            public void setTraffic_speed_entry(List<?> traffic_speed_entry) {
                this.traffic_speed_entry = traffic_speed_entry;
            }

            public List<?> getVia_waypoint() {
                return via_waypoint;
            }

            public void setVia_waypoint(List<?> via_waypoint) {
                this.via_waypoint = via_waypoint;
            }

            @NoArgsConstructor
            @Data
            public static class Distance {
                /**
                 * text : 5.6 km
                 * value : 5588
                 */

                private String text;
                private int value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }
            }

            @NoArgsConstructor
            @Data
            public static class Duration {
                /**
                 * text : 20 mins
                 * value : 1176
                 */

                private String text;
                private int value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }
            }

            @NoArgsConstructor
            @Data
            public static class EndLocation {
                /**
                 * lat : -6.9284932
                 * lng : 107.6266795
                 */

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }

            @NoArgsConstructor
            @Data
            public static class StartLocation {
                /**
                 * lat : -6.894158999999999
                 * lng : 107.631945
                 */

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }

            @NoArgsConstructor
            @Data
            public static class Steps {
                /**
                 * distance : {"text":"0.2 km","value":249}
                 * duration : {"text":"2 mins","value":106}
                 * end_location : {"lat":-6.8940237,"lng":107.6341447}
                 * html_instructions : Head <b>east</b> on <b>Jl. Sido Luhur</b> toward <b>Jl. Batik Lasem</b>
                 * polyline : {"points":"noai@sz|oSUeA[wACoB?CHoANuA"}
                 * start_location : {"lat":-6.894158999999999,"lng":107.631945}
                 * travel_mode : DRIVING
                 * maneuver : turn-left
                 */

                private DistanceX distance;
                private DurationX duration;
                private EndLocationX end_location;
                private String html_instructions;
                private Polyline polyline;
                private StartLocationX start_location;
                private String travel_mode;
                private String maneuver;

                public DistanceX getDistance() {
                    return distance;
                }

                public void setDistance(DistanceX distance) {
                    this.distance = distance;
                }

                public DurationX getDuration() {
                    return duration;
                }

                public void setDuration(DurationX duration) {
                    this.duration = duration;
                }

                public EndLocationX getEnd_location() {
                    return end_location;
                }

                public void setEnd_location(EndLocationX end_location) {
                    this.end_location = end_location;
                }

                public String getHtml_instructions() {
                    return html_instructions;
                }

                public void setHtml_instructions(String html_instructions) {
                    this.html_instructions = html_instructions;
                }

                public Polyline getPolyline() {
                    return polyline;
                }

                public void setPolyline(Polyline polyline) {
                    this.polyline = polyline;
                }

                public StartLocationX getStart_location() {
                    return start_location;
                }

                public void setStart_location(StartLocationX start_location) {
                    this.start_location = start_location;
                }

                public String getTravel_mode() {
                    return travel_mode;
                }

                public void setTravel_mode(String travel_mode) {
                    this.travel_mode = travel_mode;
                }

                public String getManeuver() {
                    return maneuver;
                }

                public void setManeuver(String maneuver) {
                    this.maneuver = maneuver;
                }

                @NoArgsConstructor
                @Data
                public static class DistanceX {
                    /**
                     * text : 0.2 km
                     * value : 249
                     */

                    private String text;
                    private int value;

                    public String getText() {
                        return text;
                    }

                    public void setText(String text) {
                        this.text = text;
                    }

                    public int getValue() {
                        return value;
                    }

                    public void setValue(int value) {
                        this.value = value;
                    }
                }

                @NoArgsConstructor
                @Data
                public static class DurationX {
                    /**
                     * text : 2 mins
                     * value : 106
                     */

                    private String text;
                    private int value;

                    public String getText() {
                        return text;
                    }

                    public void setText(String text) {
                        this.text = text;
                    }

                    public int getValue() {
                        return value;
                    }

                    public void setValue(int value) {
                        this.value = value;
                    }
                }

                @NoArgsConstructor
                @Data
                public static class EndLocationX {
                    /**
                     * lat : -6.8940237
                     * lng : 107.6341447
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }

                @NoArgsConstructor
                @Data
                public static class Polyline {
                    /**
                     * points : noai@sz|oSUeA[wACoB?CHoANuA
                     */

                    private String points;

                    public String getPoints() {
                        return points;
                    }

                    public void setPoints(String points) {
                        this.points = points;
                    }
                }

                @NoArgsConstructor
                @Data
                public static class StartLocationX {
                    /**
                     * lat : -6.894158999999999
                     * lng : 107.631945
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }
        }
    }
}
