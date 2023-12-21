export function nameTypeById(typeId: number){
  let result;
  switch(typeId){
    case 1: result="USER STORY"; break;
    case 2: result="BUG"; break;
    case 3: result="TO DO"; break;
    case 4: result="AOA"; break;
  }
  return result;
}
