function validateLoginForm() {
    const username = document.getElementById("username");
    const password = document.getElementById("password");
    if (!username || !password) {
        return true;
    }
    if (username.value.trim() === "" || password.value.trim() === "") {
        alert("Username and password are required.");
        if (username.value.trim() === "") {
            username.focus();
        } else {
            password.focus();
        }
        return false;
    }
    return true;
}

function validateReservationForm() {
    const guest = document.querySelector("input[name='guestName']");
    const phone = document.querySelector("input[name='contactNo']");
    const checkIn = document.querySelector("input[name='checkInDate']");
    const checkOut = document.querySelector("input[name='checkOutDate']");
    if (!guest || !phone || !checkIn || !checkOut) {
        return true;
    }
    if (guest.value.trim().length < 2) {
        alert("Guest name must be at least 2 characters.");
        guest.focus();
        return false;
    }

    const digits = phone.value.replace(/\D/g, "");
    if (!/^[0-9]{10,12}$/.test(digits)) {
        alert("Phone number must contain 10-12 digits.");
        phone.focus();
        return false;
    }

    const checkInDate = new Date(checkIn.value);
    const checkOutDate = new Date(checkOut.value);
    if (!Number.isNaN(checkInDate.getTime()) && !Number.isNaN(checkOutDate.getTime()) && checkOutDate <= checkInDate) {
        alert("Check-out must be after check-in.");
        checkOut.focus();
        return false;
    }
    return true;
}
