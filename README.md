# Event Booking API

Ushbu loyiha tadbirlarni (events) yaratish va ularga ro'yxatdan o'tish uchun mo'ljallangan to'liq funksional RESTful API hisoblanadi. API foydalanuvchilarni JWT (JSON Web Token) orqali autentifikatsiya qiladi va barcha endpoint'lar Swagger (OpenAPI 3) yordamida to'liq hujjatlashtirilgan. Loyiha Docker va Docker Compose yordamida osongina ishga tushiriladi.

## 🚀 Asosiy funksionallik

-   Foydalanuvchilarni ro'yxatdan o'tkazish va tizimga kirish (JWT autentifikatsiya).
-   Autentifikatsiyadan o'tgan foydalanuvchilar tomonidan tadbir yaratish, o'zgartirish va o'chirish.
-   Barcha tadbirlar ro'yxatini va ID bo'yicha alohida tadbirni ko'rish.
-   Foydalanuvchining o'zi yaratgan tadbirlar ro'yxatini olish.
-   Boshqa foydalanuvchilarning tadbirlariga ro'yxatdan o'tish.
-   O'zining ro'yxatdan o'tgan tadbirlari ro'yxatini ko'rish.

## 🛠️ Ishlatilgan texnologiyalar

-   **Backend**:
    -   Java 21
    -   Spring Boot 3.5.4
    -   Spring Web (RESTful API)
    -   Spring Data JPA (Hibernate)
    -   Spring Security
-   **Ma'lumotlar bazasi**:
    -   PostgreSQL
-   **Autentifikatsiya**:
    -   JSON Web Tokens (JWT)
-   **Hujjatlashtirish**:
    -   Swagger / OpenAPI 3 (springdoc-openapi)
-   **Konteynerlashtirish**:
    -   Docker
    -   Docker Compose
-   **Build vositasi**:
    -   Gradle (Groovy)
-   **Yordamchi kutubxonalar**:
    -   Lombok
    -   Jackson (JSON Processing)

## ⚙️ Loyihani ishga tushirish

Loyiha to'liq konteynerlashtirilgan, shuning uchun uni ishga tushirish uchun sizga faqat **Docker** va **Docker Compose** o'rnatilgan bo'lishi kifoya. Java yoki PostgreSQL'ni alohida o'rnatish shart emas.

### 1-qadam: Repository'ni yuklab olish

```bash
git clone https://github.com/muhammadjonsaidov/event-booking-api.git
cd event-booking-api
```

### 2-qadam: Docker orqali ishga tushirish (Tavsiya etiladi)

Loyiha papkasida terminalni oching va quyidagi yagona buyruqni bajaring:

```bash
docker-compose up -d --build 
```

Bu buyruq quyidagi amallarni avtomatik ravishda bajaradi:
1.  Loyihani build qilish uchun vaqtinchalik Gradle konteynerini yaratadi va `.jar` faylni yig'adi (`multi-stage build`).
2.  PostgreSQL ma'lumotlar bazasi uchun alohida konteynerni ishga tushiradi.
3.  Yaratilgan `.jar` fayl bilan asosiy ilova (API) uchun konteynerni ishga tushiradi.
4.  Ilova va ma'lumotlar bazasini ular bir-biri bilan "gaplasha oladigan" umumiy Docker tarmog'iga bog'laydi.

Ilova to'liq ishga tushganini terminaldan bilib olishingiz mumkin.

### 3-qadam: Ilovadan foydalanish

-   **API serveri**: `http://localhost:8080` manzilida ishlaydi.
-   **Swagger UI (API hujjatlari)**: `http://localhost:8080/swagger-ui.html` manzilida joylashgan. Barcha endpoint'larni shu yerdan ko'rish, tavsifini o'qish va test qilib ko'rish mumkin.

## 📝 API Endpoint'lar

Barcha endpoint'lar tavsifi va ularni sinab ko'rish uchun interaktiv interfeys **Swagger UI**'da mavjud:
[**http://localhost:8080/swagger-ui.html**](http://localhost:8080/swagger-ui.html)

**Eslatma:** `/api/auth` endpoint'laridan tashqari barcha endpoint'lar JWT token talab qiladi. Tokenni `login` orqali olib, Swagger UI'dagi **"Authorize"** tugmasi orqali kiritishingiz kerak.

---

### **01. Authentication**
*Foydalanuvchini ro'yxatdan o'tkazish va tizimga kirish.*

-   `POST /api/auth/register` - Yangi foydalanuvchini ro'yxatdan o'tkazish.
-   `POST /api/auth/login` - Tizimga kirish va JWT token olish.

---

### **02. Events**
*Tadbirlarni yaratish, ko'rish, yangilash va o'chirish.*

-   `POST   /api/events` - Yangi tadbir yaratish.
-   `GET    /api/events` - Barcha mavjud tadbirlar ro'yxatini olish.
-   `GET    /api/events/my-events` - Joriy foydalanuvchi tomonidan tashkil etilgan tadbirlarni olish.
-   `GET    /api/events/{id}` - ID bo'yicha bitta tadbir ma'lumotini olish.
-   `PUT    /api/events/{id}` - Mavjud tadbirni yangilash (faqat tashkilotchi).
-   `DELETE /api/events/{id}` - Mavjud tadbirni o'chirish (faqat tashkilotchi).

---
### **03. Registrations**
*Tadbirlarga ro'yxatdan o'tishni boshqarish.*

-   `POST /api/events/{eventId}/register` - Muayyan tadbirga ro'yxatdan o'tish.
-   `GET  /api/registrations/my` - Joriy foydalanuvchi ro'yxatdan o'tgan barcha tadbirlarni ko'rish.


## ⏹️ Ilovani to'xtatish

Ilovani va u bilan bog'liq barcha konteynerlarni to'xtatish uchun terminalga quyidagi buyruqni kiriting:

```bash
docker-compose down
```
Ma'lumotlar bazasidagi ma'lumotlarni ham to'liq o'chirish uchun:
```bash
docker-compose down -v
```