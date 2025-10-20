# 🔐 RSA - Geração de Chaves Criptográficas

Este documento explica o processo utilizado para gerar as **chaves públicas e privadas** do algoritmo **RSA**, conforme demonstrado na planilha `RSA_Tabela.xlsx`.

---

## 📘 Conceito

O **RSA (Rivest–Shamir–Adleman)** é um algoritmo de criptografia assimétrica que utiliza **duas chaves diferentes**:
- **Chave Pública (N, E)** → usada para **criptografar** mensagens.
- **Chave Privada (N, D)** → usada para **descriptografar** mensagens.

---

## ⚙️ Passo a Passo dos Cálculos

| Etapa | Descrição | Fórmula | Exemplo |
|-------|------------|----------|----------|
| **1️⃣ Escolher dois números primos (P e Q)** | São os valores base da criptografia. | — | P = 857, Q = 859 |
| **2️⃣ Calcular o módulo (N)** | Produto dos dois primos. | `N = P × Q` | `N = 857 × 859 = 736163` |
| **3️⃣ Calcular a Função Totiente (φ(N))** | Determina o número de coprimos de N. | `φ(N) = (P - 1) × (Q - 1)` | `φ(N) = 856 × 858 = 734448` |
| **4️⃣ Escolher o expoente público (E)** | Número entre 1 e φ(N), **coprimo** de φ(N). | mdc(E, φ(N)) = 1 | `E = 65537` |
| **5️⃣ Calcular o expoente privado (D)** | Inverso multiplicativo de E mod φ(N). | `D × E ≡ 1 (mod φ(N))` | `D = 708929` |

---

## 🔑 Chaves Resultantes

| Tipo | Módulo (N) | Expoente | Uso |
|------|-------------|----------|-----|
| **Chave Pública** | 736163 | 65537 | Criptografia |
| **Chave Privada** | 736163 | 708929 | Descriptografia |

---

## 📊 Estrutura da Planilha

A planilha `RSA_Tabela.xlsx` contém as seguintes seções:

| Campo | Descrição |
|--------|------------|
| **P, Q** | Números primos escolhidos manualmente |
| **N** | Resultado de P × Q |
| **φ(N)** | Função totiente |
| **E** | Expoente público (usado para criptografar) |
| **D** | Expoente privado (usado para descriptografar) |

---

## 🧮 Como validar os cálculos

Você pode verificar o valor de **D** utilizando o site:

🔗 [https://planetcalc.com/3311/](https://planetcalc.com/3311/)

- **Integer (E):** 65537  
- **Modulo (φ(N)):** 734448  
➡️ O resultado será **708929**, que é o valor de D.

---

## 🧠 Observações

- O valor **E = 65537** é um padrão amplamente usado por ser seguro e eficiente.  
- O valor de **D** deve sempre satisfazer a condição: `D × E ≡ 1 (mod φ(N))`.  
- É importante **manter D em sigilo**, pois ele permite a decifragem das mensagens criptografadas.

---
## IDE utilizada: Intelli J


