export function nameStatusById(statusId: number){
  let result;
  switch(statusId){
    case 1: result="TO DO"; break;
    case 2: result="IN PROGRESS"; break;
    case 3: result="CODE REVIEW"; break;
    case 4: result="IN TESTS"; break;
    case 5: result="DONE"; break;
  }
  return result;
}
