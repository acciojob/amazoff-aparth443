package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {

     HashMap<String,Order> orderHashMap = new HashMap<>();
     HashMap<String,DeliveryPartner> partnerHashMap = new HashMap<>();
     HashMap<String,List<String>> pairHashMap = new HashMap<>();

    public String addOrder(Order order){
        String orderId = order.getId();
        orderHashMap.put(orderId,order);
        return "New order added successfully";
    }

    public String addPartner(String partnerId){
        DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
        partnerHashMap.put(partnerId,deliveryPartner);
        return "New delivery partner added successfully";
    }


    public String addOrderPartnerPair(String orderId, String partnerId){
        DeliveryPartner deliveryPartner = partnerHashMap.get(partnerId);
        if(pairHashMap.containsKey(partnerId)){
            pairHashMap.get(partnerId).add(orderId);
        }else{
            List<String> ls = new ArrayList<>();
            ls.add(orderId);
            pairHashMap.put(partnerId,ls);
        }
        deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders()+1);
        return "New order-partner pair added successfully";
    }

    public Order getOrderById(String orderId){
        return orderHashMap.get(orderId);
    }
    public DeliveryPartner getPartnerById(String partnerId){
        return partnerHashMap.get(partnerId);
    }
    public Integer getOrderCountByPartnerId(String partnerId){
        return pairHashMap.get(partnerId).size();
    }
    public List<String> getOrdersByPartnerId(String partnerId){
        return pairHashMap.get(partnerId);
    }

    public List<String> getAllOrders(){
        List<String> orders = new ArrayList<>();
        for(Order order: orderHashMap.values()){
            orders.add(order.getId());
        }
       return orders;
    }

    public Integer getCountOfUnassignedOrders(){
        int totalOrders = orderHashMap.size();
        int totalAssignedOrders;
        Set<String> assignedOrders = new HashSet<>();
        for(List<String> ls : pairHashMap.values()){
            assignedOrders.addAll(ls);
        }
        totalAssignedOrders = assignedOrders.size();
        return totalOrders - totalAssignedOrders;
    }
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        List<String> orderIds = pairHashMap.get(partnerId);
        String hr = time.substring(0, 2);
        String min = time.substring(3);
        int hour = Integer.parseInt(hr);
        int minute = Integer.parseInt(min);
        int actualTime = hour * 60 + minute;
        int count = 0;
        for(String id: orderIds){
            Order order = orderHashMap.get(id);
            int deliveryTime = order.getDeliveryTime();
            if(deliveryTime>actualTime){
                count++;
            }
        }
        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){
        String time = null;
        List<String> orderIds = pairHashMap.get(partnerId);
        int lastTime = -1;
        for(String id: orderIds){
            Order order = orderHashMap.get(id);
            int deliveryTime = order.getDeliveryTime();
            String dTS = order.getDeliveryTimeStr();
            if(lastTime<deliveryTime){
                lastTime = deliveryTime;
                time = dTS;
            }
        }
        return time;
    }

    public String deletePartnerById(String partnerId){
        pairHashMap.remove(partnerId);
        pairHashMap.remove(partnerId);
        return partnerId + " removed successfully";
    }
    public String deleteOrderById(String orderId){
        orderHashMap.remove(orderId);
        for(List<String> ls : pairHashMap.values()){
            for(int i=0;i<ls.size();i++){
                if(ls.get(i).equals(orderId)){
                    ls.remove(orderId);
                }
            }
        }
        return orderId + " removed successfully";
    }
}
