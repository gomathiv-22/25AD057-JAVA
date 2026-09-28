const API = {
    camps: "/api/camps",
    families: "/api/families",
    supplies: "/api/supplies",
    distributions: "/api/distributions"
};


/* =========================
   PAGE NAVIGATION
========================= */

function showSection(sectionId, button = null) {

    document.querySelectorAll(".content-section").forEach(section => {
        section.classList.remove("active-section");
    });

    document.getElementById(sectionId).classList.add("active-section");

    document.querySelectorAll(".nav-item").forEach(item => {
        item.classList.remove("active");
    });

    if (button) {
        button.classList.add("active");
    }

    const titles = {
        dashboard: "Dashboard",
        camps: "Camps",
        families: "Families",
        supplies: "Supplies",
        distributions: "Distributions"
    };

    document.getElementById("pageTitle").textContent = titles[sectionId];

    if (sectionId === "camps") {
        loadCamps();
    }

    if (sectionId === "families") {
        loadFamilies();
    }

    if (sectionId === "supplies") {
        loadSupplies();
    }

    if (sectionId === "distributions") {
        loadDistributions();
    }
}


/* =========================
   DASHBOARD
========================= */

async function loadDashboard() {

    try {

        const [camps, families, supplies, distributions] =
            await Promise.all([
                fetch(API.camps).then(res => res.json()),
                fetch(API.families).then(res => res.json()),
                fetch(API.supplies).then(res => res.json()),
                fetch(API.distributions).then(res => res.json())
            ]);

        document.getElementById("campCount").textContent = camps.length;
        document.getElementById("familyCount").textContent = families.length;
        document.getElementById("supplyCount").textContent = supplies.length;
        document.getElementById("distributionCount").textContent =
            distributions.length;

    } catch (error) {
        console.error("Dashboard loading error:", error);
    }
}


/* =========================
   CAMPS
========================= */

async function loadCamps() {

    try {

        const response = await fetch(API.camps);
        const camps = await response.json();

        const table = document.getElementById("campTableBody");

        if (camps.length === 0) {
            table.innerHTML =
                `<tr><td colspan="5" class="empty-row">
                    No camps found
                 </td></tr>`;
            return;
        }

        table.innerHTML = camps.map(camp => `
            <tr>
                <td>${camp.campId}</td>
                <td>${camp.campName}</td>
                <td>${camp.location}</td>
                <td>${camp.capacity}</td>
                <td>
                    <button class="edit-btn"
                        onclick="editCamp(${camp.campId})">
                        Edit
                    </button>

                    <button class="delete-btn"
                        onclick="deleteCamp(${camp.campId})">
                        Delete
                    </button>
                </td>
            </tr>
        `).join("");

    } catch (error) {
        showToast("Unable to load camps");
        console.error(error);
    }
}


document.getElementById("campForm").addEventListener("submit", async function (event) {

    event.preventDefault();

    const id = document.getElementById("campId").value;

    const camp = {
        campName: document.getElementById("campName").value,
        location: document.getElementById("campLocation").value,
        capacity: Number(document.getElementById("campCapacity").value)
    };

    try {

        const url = id ? `${API.camps}/${id}` : API.camps;

        const response = await fetch(url, {
            method: id ? "PUT" : "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(camp)
        });

        if (!response.ok) {
            throw new Error();
        }

        showToast(id ? "Camp updated successfully" : "Camp added successfully");

        resetCampForm();
        loadCamps();
        loadDashboard();

    } catch (error) {
        showToast("Failed to save camp");
        console.error(error);
    }
});


async function editCamp(id) {

    try {

        const response = await fetch(`${API.camps}/${id}`);
        const camp = await response.json();

        document.getElementById("campId").value = camp.campId;
        document.getElementById("campName").value = camp.campName;
        document.getElementById("campLocation").value = camp.location;
        document.getElementById("campCapacity").value = camp.capacity;

        document.getElementById("campFormTitle").textContent =
            "Edit Camp";

        window.scrollTo({ top: 0, behavior: "smooth" });

    } catch (error) {
        showToast("Unable to load camp");
    }
}


async function deleteCamp(id) {

    if (!confirm("Are you sure you want to delete this camp?")) {
        return;
    }

    try {

        const response = await fetch(`${API.camps}/${id}`, {
            method: "DELETE"
        });

        if (!response.ok) {
            throw new Error();
        }

        showToast("Camp deleted successfully");

        loadCamps();
        loadDashboard();

    } catch (error) {
        showToast("Failed to delete camp");
    }
}


function resetCampForm() {

    document.getElementById("campForm").reset();
    document.getElementById("campId").value = "";
    document.getElementById("campFormTitle").textContent =
        "Add New Camp";
}


/* =========================
   FAMILIES
========================= */

async function loadFamilies() {

    try {

        const response = await fetch(API.families);
        const families = await response.json();

        const table = document.getElementById("familyTableBody");

        if (families.length === 0) {
            table.innerHTML =
                `<tr><td colspan="5" class="empty-row">
                    No families found
                 </td></tr>`;
            return;
        }

        table.innerHTML = families.map(family => `
            <tr>
                <td>${family.familyId}</td>
                <td>${family.familyName}</td>
                <td>${family.headcount}</td>
                <td>${family.campId}</td>
                <td>
                    <button class="edit-btn"
                        onclick="editFamily(${family.familyId})">
                        Edit
                    </button>

                    <button class="delete-btn"
                        onclick="deleteFamily(${family.familyId})">
                        Delete
                    </button>
                </td>
            </tr>
        `).join("");

    } catch (error) {
        showToast("Unable to load families");
    }
}


document.getElementById("familyForm").addEventListener("submit", async function (event) {

    event.preventDefault();

    const id = document.getElementById("familyId").value;

    const family = {
        familyName: document.getElementById("familyName").value,
        headcount: Number(document.getElementById("familyHeadcount").value),
        campId: Number(document.getElementById("familyCampId").value)
    };

    try {

        const url = id ? `${API.families}/${id}` : API.families;

        const response = await fetch(url, {
            method: id ? "PUT" : "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(family)
        });

        if (!response.ok) {
            throw new Error();
        }

        showToast(id ? "Family updated successfully" : "Family added successfully");

        resetFamilyForm();
        loadFamilies();
        loadDashboard();

    } catch (error) {
        showToast("Failed to save family");
    }
});


async function editFamily(id) {

    try {

        const response = await fetch(`${API.families}/${id}`);
        const family = await response.json();

        document.getElementById("familyId").value = family.familyId;
        document.getElementById("familyName").value = family.familyName;
        document.getElementById("familyHeadcount").value = family.headcount;
        document.getElementById("familyCampId").value = family.campId;

        window.scrollTo({ top: 0, behavior: "smooth" });

    } catch (error) {
        showToast("Unable to load family");
    }
}


async function deleteFamily(id) {

    if (!confirm("Are you sure you want to delete this family?")) {
        return;
    }

    try {

        const response = await fetch(`${API.families}/${id}`, {
            method: "DELETE"
        });

        if (!response.ok) {
            throw new Error();
        }

        showToast("Family deleted successfully");

        loadFamilies();
        loadDashboard();

    } catch (error) {
        showToast("Failed to delete family");
    }
}


function resetFamilyForm() {

    document.getElementById("familyForm").reset();
    document.getElementById("familyId").value = "";
}


/* =========================
   SUPPLIES
========================= */

async function loadSupplies() {

    try {

        const response = await fetch(API.supplies);
        const supplies = await response.json();

        const table = document.getElementById("supplyTableBody");

        if (supplies.length === 0) {
            table.innerHTML =
                `<tr><td colspan="5" class="empty-row">
                    No supplies found
                 </td></tr>`;
            return;
        }

        table.innerHTML = supplies.map(supply => `
            <tr>
                <td>${supply.supplyId}</td>
                <td>${supply.supplyType}</td>
                <td>${supply.quantity}</td>
                <td>${supply.campId}</td>
                <td>
                    <button class="edit-btn"
                        onclick="editSupply(${supply.supplyId})">
                        Edit
                    </button>

                    <button class="delete-btn"
                        onclick="deleteSupply(${supply.supplyId})">
                        Delete
                    </button>
                </td>
            </tr>
        `).join("");

    } catch (error) {
        showToast("Unable to load supplies");
    }
}


document.getElementById("supplyForm").addEventListener("submit", async function (event) {

    event.preventDefault();

    const id = document.getElementById("supplyId").value;

    const supply = {
        supplyType: document.getElementById("supplyType").value,
        quantity: Number(document.getElementById("supplyQuantity").value),
        campId: Number(document.getElementById("supplyCampId").value)
    };

    try {

        const url = id ? `${API.supplies}/${id}` : API.supplies;

        const response = await fetch(url, {
            method: id ? "PUT" : "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(supply)
        });

        if (!response.ok) {
            throw new Error();
        }

        showToast(id ? "Supply updated successfully" : "Supply added successfully");

        resetSupplyForm();
        loadSupplies();
        loadDashboard();

    } catch (error) {
        showToast("Failed to save supply");
    }
});


async function editSupply(id) {

    try {

        const response = await fetch(`${API.supplies}/${id}`);
        const supply = await response.json();

        document.getElementById("supplyId").value = supply.supplyId;
        document.getElementById("supplyType").value = supply.supplyType;
        document.getElementById("supplyQuantity").value = supply.quantity;
        document.getElementById("supplyCampId").value = supply.campId;

        window.scrollTo({ top: 0, behavior: "smooth" });

    } catch (error) {
        showToast("Unable to load supply");
    }
}


async function deleteSupply(id) {

    if (!confirm("Are you sure you want to delete this supply?")) {
        return;
    }

    try {

        const response = await fetch(`${API.supplies}/${id}`, {
            method: "DELETE"
        });

        if (!response.ok) {
            throw new Error();
        }

        showToast("Supply deleted successfully");

        loadSupplies();
        loadDashboard();

    } catch (error) {
        showToast("Failed to delete supply");
    }
}


function resetSupplyForm() {

    document.getElementById("supplyForm").reset();
    document.getElementById("supplyId").value = "";
}


/* =========================
   DISTRIBUTIONS
========================= */

async function loadDistributions() {

    try {

        const response = await fetch(API.distributions);
        const distributions = await response.json();

        const table = document.getElementById("distributionTableBody");

        if (distributions.length === 0) {
            table.innerHTML =
                `<tr><td colspan="5" class="empty-row">
                    No distributions found
                 </td></tr>`;
            return;
        }

        table.innerHTML = distributions.map(distribution => `
            <tr>
                <td>${distribution.distributionId}</td>
                <td>${distribution.familyId}</td>
                <td>${distribution.supplyId}</td>
                <td>${distribution.quantity}</td>
                <td>
                    <button class="edit-btn"
                        onclick="editDistribution(${distribution.distributionId})">
                        Edit
                    </button>

                    <button class="delete-btn"
                        onclick="deleteDistribution(${distribution.distributionId})">
                        Delete
                    </button>
                </td>
            </tr>
        `).join("");

    } catch (error) {
        showToast("Unable to load distributions");
    }
}


document.getElementById("distributionForm").addEventListener("submit", async function (event) {

    event.preventDefault();

    const id = document.getElementById("distributionId").value;

    const distribution = {
        familyId: Number(document.getElementById("distributionFamilyId").value),
        supplyId: Number(document.getElementById("distributionSupplyId").value),
        quantity: Number(document.getElementById("distributionQuantity").value)
    };

    try {

        const url = id
            ? `${API.distributions}/${id}`
            : API.distributions;

        const response = await fetch(url, {
            method: id ? "PUT" : "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(distribution)
        });

        if (!response.ok) {
            throw new Error();
        }

        showToast(
            id
                ? "Distribution updated successfully"
                : "Distribution added successfully"
        );

        resetDistributionForm();
        loadDistributions();
        loadDashboard();

    } catch (error) {
        showToast("Failed to save distribution");
    }
});


async function editDistribution(id) {

    try {

        const response =
            await fetch(`${API.distributions}/${id}`);

        const distribution = await response.json();

        document.getElementById("distributionId").value =
            distribution.distributionId;

        document.getElementById("distributionFamilyId").value =
            distribution.familyId;

        document.getElementById("distributionSupplyId").value =
            distribution.supplyId;

        document.getElementById("distributionQuantity").value =
            distribution.quantity;

        window.scrollTo({ top: 0, behavior: "smooth" });

    } catch (error) {
        showToast("Unable to load distribution");
    }
}


async function deleteDistribution(id) {

    if (!confirm("Are you sure you want to delete this distribution?")) {
        return;
    }

    try {

        const response =
            await fetch(`${API.distributions}/${id}`, {
                method: "DELETE"
            });

        if (!response.ok) {
            throw new Error();
        }

        showToast("Distribution deleted successfully");

        loadDistributions();
        loadDashboard();

    } catch (error) {
        showToast("Failed to delete distribution");
    }
}


function resetDistributionForm() {

    document.getElementById("distributionForm").reset();
    document.getElementById("distributionId").value = "";
}


/* =========================
   TOAST
========================= */

function showToast(message) {

    const toast = document.getElementById("toast");

    toast.textContent = message;
    toast.classList.add("show");

    setTimeout(() => {
        toast.classList.remove("show");
    }, 2500);
}


/* =========================
   INITIAL LOAD
========================= */

document.addEventListener("DOMContentLoaded", () => {

    loadDashboard();
    loadCamps();
    loadFamilies();
    loadSupplies();
    loadDistributions();

});