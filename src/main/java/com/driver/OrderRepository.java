package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {

     HashMap<String,Order> orderHashMap = new HashMap<>();
     HashMap<String,DeliveryPartner> partnerHashMap = new HashMap<>();
     HashMap<DeliveryPartner,List<Order>> deliveryPartnerOrderListHashMap= new HashMap<>();
     HashMap<Order,DeliveryPartner> orderDeliveryPartnerHashMap = new HashMap<>();

    public void addOrder(Order order){
        String orderId = order.getId();
        orderHashMap.put(orderId,order);
        return;
    }

    public void addPartner(String partnerId){
        DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
        partnerHashMap.put(partnerId,deliveryPartner);
        return;
    }


    public void addOrderPartnerPair(String orderId, String partnerId){
        Order order = orderHashMap.get(orderId);
        DeliveryPartner deliveryPartner = partnerHashMap.get(partnerId);
        Integer numberOfOrders = deliveryPartner.getNumberOfOrders();
        deliveryPartner.setNumberOfOrders(numberOfOrders+1);
        orderDeliveryPartnerHashMap.put(order,deliveryPartner);
        List<Order> currentOrders = deliveryPartnerOrderListHashMap.get(deliveryPartner);
        currentOrders.add(order);
        deliveryPartnerOrderListHashMap.put(deliveryPartner,currentOrders);
        return;
    }

    public Order getOrderById(String orderId){
        return orderHashMap.get(orderId);
    }
    public DeliveryPartner getPartnerById(String partnerId){
        return partnerHashMap.get(partnerId);
    }
    public Integer getOrderCountByPartnerId(String partnerId){

        Integer orderCount = deliveryPartnerOrderListHashMap.get(partnerId).size();
        return orderCount;
    }
    public List<String> getOrdersByPartnerId(String partnerId){
        DeliveryPartner deliveryPartner = partnerHashMap.get(partnerId);

        List<Order> ls = deliveryPartnerOrderListHashMap.get(deliveryPartner);
        List<String> ans = new ArrayList<>();
        for(int i=0;i<ls.size();i++){
            ans.add(ls.get(i).getId());
        }
        return ans;
    }

    public List<String> getAllOrders(){
        List<String> orders = new ArrayList<>();
        for(String mapElement : orderHashMap.keySet()){
            orders.add(mapElement);
        }
       return orders;
    }

    public Integer getCountOfUnassignedOrders(){
        Integer total = orderHashMap.size();
        for(List<Order> ls: deliveryPartnerOrderListHashMap.values()){
            total -= ls.size();
        }
        return total;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        String hr = time.substring(0, 2);
        String min = time.substring(3, 5);
        int hour = Integer.parseInt(hr);
        int minute = Integer.parseInt(min);
        int actualTime = hour * 60 + minute;

        DeliveryPartner deliveryPartner = partnerHashMap.get(partnerId);
        List<Order> ls = deliveryPartnerOrderListHashMap.get(deliveryPartner);

        Integer count = 0;

        for (Order l : ls)
            if (l.getDeliveryTime() > actualTime) count++;
        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){
        String time = null;
        DeliveryPartner deliveryPartner = partnerHashMap.get(partnerId);
        List<Order> ls = deliveryPartnerOrderListHashMap.get(deliveryPartner);
        int maxTime = 0;
        for(Order l: ls){
            if(l.getDeliveryTime()>maxTime){
                maxTime = l.getDeliveryTime();
            }
        }
        int hr = maxTime/60;
        int min = maxTime%60;
        String hour;
        String minute;
        if(hr<10){
            hour = "0";
            hour += String.valueOf(hr);
        }else{
            hour = String.valueOf(hr);
        }

        if(min<10){
            minute = "0";
            minute += String.valueOf(min);
        }else{
            minute = String.valueOf(min);
        }
        String timeframe = hour + ":" + minute;
        return timeframe;
    }

    public void deletePartnerById(String partnerId){
        DeliveryPartner deliveryPartner = partnerHashMap.get(partnerId);
        partnerHashMap.remove(partnerId);
        deliveryPartnerOrderListHashMap.remove(deliveryPartner);
        return;
    }
    public void deleteOrderById(String orderId){
        Order order = orderHashMap.get(orderId);
        DeliveryPartner deliveryPartner = orderDeliveryPartnerHashMap.get(order);
        List<Order> ls = deliveryPartnerOrderListHashMap.get(deliveryPartner);
        ls.remove(order);
        orderHashMap.remove(orderId);
        orderDeliveryPartnerHashMap.remove(order);
        return;
    }

}
