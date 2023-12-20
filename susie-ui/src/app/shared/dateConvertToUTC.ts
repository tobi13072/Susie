export function dateConvertToUTC(date: string){
  const dt = new Date(date);
  const dt2 = new Date(Date.UTC(dt.getFullYear(), dt.getMonth(), dt.getDate(), dt.getHours(), dt.getMinutes()))

  return dt2.toJSON()
}

export function dateConvertFromUTC(date: string){
  const inputDate = new Date(date);

  inputDate.setHours(inputDate.getHours()-1)
  const options: Intl.DateTimeFormatOptions = {
    weekday: 'short',
    year: 'numeric',
    month: 'short',
    day: 'numeric',
    hour: 'numeric',
    minute: 'numeric',
    second: 'numeric',
    timeZoneName: 'short',
    hour12: false
  };

  return inputDate.toLocaleDateString('en-US', options);
}
