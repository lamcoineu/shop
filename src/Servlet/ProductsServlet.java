package Servlet;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import model.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;



public class ProductsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Firestore db = (Firestore) getServletContext().getAttribute("db");


        ApiFuture<QuerySnapshot> query = db.collection("products").get();
        QuerySnapshot querySnapshot = null;
        try {
            querySnapshot = query.get();

            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            List<Product> products = new ArrayList<>();
            for (QueryDocumentSnapshot document : documents) {


                Product product = new Product();
                product.setName(document.getString("name"));
                product.setPrice(document.getLong("price"));
                product.setDescription(document.getString("description"));
                products.add(product);

                System.out.println(product.getPrice());
            }
            req.setAttribute("products",products);
            RequestDispatcher rd = req.getRequestDispatcher("/products.jsp");
            rd.forward(req, resp);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
