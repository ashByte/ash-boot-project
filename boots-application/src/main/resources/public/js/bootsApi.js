// API CALLS
/**
 * fetches all boots from the backend
 * @param {(Boot[]) => void} cb function that renders boot list into DOM
 * @returns void
 */
const fetchAllBoots = (cb) => {
  fetch("/api/v1/boots/")
    .then(res => res.json())
    .then(json => cb(json))
    .catch(renderError);
}

/**
 * fetches all different boot types from the backend
 * @param {(String[]) => void} cb function that renders boot types into dropdown options
 * @returns void
 */
const fetchBootTypes = (cb) => {
  fetch("/api/v1/boots/types/")
    .then(res => res.json())
    .then(json => cb(json))
    .catch(renderError);
}

/**
 * searches boots by different parameters
 * @param {BootQuery} bootQuery query object for filter criteria
 * @param {(Boot[]) => void} cb function to render boot search results
 */
const searchBoots = (bootQuery, cb) => {
  const queryString = Object.keys(bootQuery)
    .map((key) => `${encodeURIComponent(key)}=${encodeURIComponent(bootQuery[key])}`)
    .join('&');

  fetch(`/api/v1/boots/search?${queryString}`)
    .then(res => res.json())
    .then(json => cb(json))
    .catch(renderError);
}

// RENDERING FUNCTIONS

/**
 * alerts user if there was an error making an API request
 * @param {String} message error message to show
 */
const renderError = (message) => {
  alert(`Error calling Boots API: ${message}`);
}

/**
 * renders list of boots into table rows
 * @param {Element} bootsTableBody DOM element to render boots table rows into
 * @param {Boot[]} boots list of boot objects
 * @returns void
 */
const renderBootsListCallback = (bootsTableBody) => (boots) => {
  while (bootsTableBody.firstChild) {
    bootsTableBody.removeChild(bootsTableBody.firstChild);
  }
  boots.forEach((boot) => {
    const bootsRow = document.createElement("tr");
    bootsRow.innerHTML = `
      <td>${boot.id}</td>
      <td>${boot.type}</td>
      <td>${boot.material}</td>
      <td>${boot.size}</td>
      <td>${boot.quantity}</td>
    `;
    bootsTableBody.appendChild(bootsRow);
  });
}

/**
 * renders boot types into dropdown options
 * @param {Element} bootTypesSelect DOM element to render options into
 * @param {String[]} bootTypes array of different boot types
 */
const renderBootTypesOptionsCallback = (bootTypesSelect) => (bootTypes) => {
  bootTypes.forEach(bootType => {
    const bootTypeOption = document.createElement("option");
    bootTypeOption.setAttribute("value", bootType);
    bootTypeOption.innerHTML = bootType;
    bootTypesSelect.appendChild(bootTypeOption);
  });
}

// calls to initialize and render data on script load
fetchAllBoots(
  renderBootsListCallback(
    document.getElementById("bootsTableBody")
  )
);

fetchBootTypes(
  renderBootTypesOptionsCallback(
    document.getElementById("bootTypesDropdown")
  )
);