package com.ticketflow.patterns.comportamiento;

import java.util.Stack;





public class InvokerCompras {
    private final Stack<IComando> historial = new Stack<>();

    public void ejecutarComando(IComando comando) {
        comando.ejecutar();
        historial.push(comando);
    }

    public void deshacerUltimo() {
        if (!historial.isEmpty()) {
            IComando comando = historial.pop();
            comando.deshacer();
        } else {
            System.out.println("[Invoker] No hay comandos para deshacer.");
        }
    }
}
