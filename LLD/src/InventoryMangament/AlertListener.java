public interface AlertListener{

    void onLowStock(String warehouseId, String productId, int currentQty);
}

/* Example Of Implementations */

/*

class EmailAlertListener implements AlertListener:
    onLowStock(warehouseId, productId, currentQuantity)
        sendEmail("Stock alert: " + productId + " is low in " + warehouseId)

class WebhookAlertListener implements AlertListener:
    onLowStock(warehouseId, productId, currentQuantity)
        httpPost("/alerts", {warehouse: warehouseId, product: productId, qty: currentQuantity})

class LoggingAlertListener implements AlertListener:
    onLowStock(warehouseId, productId, currentQuantity)
        log("WARNING: " + warehouseId + " has only " + currentQuantity + " of " + productId)
*/