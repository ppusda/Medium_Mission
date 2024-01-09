import {baseUrl} from "./stores.js";
import {toastWarning} from "./toastr.js";

let url = '';
const unsubscribe = baseUrl.subscribe(value => {
  url = value;
});

export async function uploadImage(formData) {
  const response = await fetch(`${url}/tui/image`, {
    method : 'POST',
    credentials: 'include',
    body : formData,
  });

  if (!response.ok) {
    const errorData = await response.json();
    toastWarning(errorData.message);
    return;
  }

  return await response.text();
}

export async function downloadImage(filename) {
  const response = await fetch(`${url}/tui/image?filename=${filename}`, {
    method : 'GET',
    credentials: 'include',
  });

  if (!response.ok) {
    const errorData = await response.json();
    toastWarning(errorData.message);
    return;
  }

  return response.url;
}