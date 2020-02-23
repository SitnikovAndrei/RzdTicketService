package com.tickets.rzd.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TicketDTO implements Serializable {
        private String number;
        private String number2;
        private float type;
        private float typeEx;
        private float depth;
        private boolean elReg;
        private boolean deferredPayment;
        private boolean varPrice;
        private float code0;
        private float code1;
        private boolean bEntire;
        private String trainName;
        private boolean bFirm;
        private String brand;
        private String carrier;
        private String route0;
        private String route1;
        private float routeCode0;
        private float routeCode1;
        private String trDate0;
        private String trTime0;
        private String station0;
        private String station1;
        private String date0;
        private String time0;
        private String date1;
        private String time1;
        private String timeInWay;
        private float flMsk;
        private float train_id;
        private List<ServiceCategoriesDTO> serviceCategories;
        private boolean nonRefundable;
        private boolean msr;

        public List<ServiceCategoriesDTO> getServiceCategories() {
            return serviceCategories;
        }

        public void setServiceCategories(List<ServiceCategoriesDTO> serviceCategories) {
            this.serviceCategories = serviceCategories;
        }


    // Getter Methods

        public String getNumber() {
            return number;
        }

        public String getNumber2() {
            return number2;
        }

        public float getType() {
            return type;
        }

        public float getTypeEx() {
            return typeEx;
        }

        public float getDepth() {
            return depth;
        }

        public boolean getElReg() {
            return elReg;
        }

        public boolean getDeferredPayment() {
            return deferredPayment;
        }

        public boolean getVarPrice() {
            return varPrice;
        }

        public float getCode0() {
            return code0;
        }

        public float getCode1() {
            return code1;
        }

        public boolean getBEntire() {
            return bEntire;
        }

        public String getTrainName() {
            return trainName;
        }

        public boolean getBFirm() {
            return bFirm;
        }

        public String getBrand() {
            return brand;
        }

        public String getCarrier() {
            return carrier;
        }

        public String getRoute0() {
            return route0;
        }

        public String getRoute1() {
            return route1;
        }

        public float getRouteCode0() {
            return routeCode0;
        }

        public float getRouteCode1() {
            return routeCode1;
        }

        public String getTrDate0() {
            return trDate0;
        }

        public String getTrTime0() {
            return trTime0;
        }

        public String getStation0() {
            return station0;
        }

        public String getStation1() {
            return station1;
        }

        public String getDate0() {
            return date0;
        }

        public String getTime0() {
            return time0;
        }

        public String getDate1() {
            return date1;
        }

        public String getTime1() {
            return time1;
        }

        public String getTimeInWay() {
            return timeInWay;
        }

        public float getFlMsk() {
            return flMsk;
        }

        public float getTrain_id() {
            return train_id;
        }

        public boolean getNonRefundable() {
            return nonRefundable;
        }

        public boolean getMsr() {
            return msr;
        }

        // Setter Methods

        public void setNumber(String number) {
            this.number = number;
        }

        public void setNumber2(String number2) {
            this.number2 = number2;
        }

        public void setType(float type) {
            this.type = type;
        }

        public void setTypeEx(float typeEx) {
            this.typeEx = typeEx;
        }

        public void setDepth(float depth) {
            this.depth = depth;
        }


        public void setElReg(boolean elReg) {
            this.elReg = elReg;
        }

        public void setDeferredPayment(boolean deferredPayment) {
            this.deferredPayment = deferredPayment;
        }

        public void setVarPrice(boolean varPrice) {
            this.varPrice = varPrice;
        }

        public void setCode0(float code0) {
            this.code0 = code0;
        }

        public void setCode1(float code1) {
            this.code1 = code1;
        }

        public void setBEntire(boolean bEntire) {
            this.bEntire = bEntire;
        }

        public void setTrainName(String trainName) {
            this.trainName = trainName;
        }

        public void setBFirm(boolean bFirm) {
            this.bFirm = bFirm;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public void setCarrier(String carrier) {
            this.carrier = carrier;
        }

        public void setRoute0(String route0) {
            this.route0 = route0;
        }

        public void setRoute1(String route1) {
            this.route1 = route1;
        }

        public void setRouteCode0(float routeCode0) {
            this.routeCode0 = routeCode0;
        }

        public void setRouteCode1(float routeCode1) {
            this.routeCode1 = routeCode1;
        }

        public void setTrDate0(String trDate0) {
            this.trDate0 = trDate0;
        }

        public void setTrTime0(String trTime0) {
            this.trTime0 = trTime0;
        }

        public void setStation0(String station0) {
            this.station0 = station0;
        }

        public void setStation1(String station1) {
            this.station1 = station1;
        }

        public void setDate0(String date0) {
            this.date0 = date0;
        }

        public void setTime0(String time0) {
            this.time0 = time0;
        }

        public void setDate1(String date1) {
            this.date1 = date1;
        }

        public void setTime1(String time1) {
            this.time1 = time1;
        }

        public void setTimeInWay(String timeInWay) {
            this.timeInWay = timeInWay;
        }

        public void setFlMsk(float flMsk) {
            this.flMsk = flMsk;
        }

        public void setTrain_id(float train_id) {
            this.train_id = train_id;
        }

        public void setNonRefundable(boolean nonRefundable) {
            this.nonRefundable = nonRefundable;
        }

        public void setMsr(boolean msr) {
            this.msr = msr;
        }
    }
