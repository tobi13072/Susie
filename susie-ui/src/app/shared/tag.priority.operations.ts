export function colorPriorityTagByName(priorityName: string){
  let result;
  switch(priorityName){
    case 'CRITICAL': result="danger"; break;
    case 'HIGH': result="warning"; break;
    case 'MEDIUM': result="info"; break;
    case 'LOW': result=""; break;
    case 'TRIVIAL': result="success"; break;
  }
  return result;
}
export function colorPriorityTagById(priorityId: number){
  let result;
  switch(priorityId){
    case 1: result="danger"; break;
    case 2: result="warning"; break;
    case 3: result="info"; break;
    case 4: result=""; break;
    case 5: result="success"; break;
  }
  return result;
}

export function namePriorityById(priorityId: number){
  let result;
  switch(priorityId){
    case 1: result="CRITICAL"; break;
    case 2: result="HIGH"; break;
    case 3: result="MEDIUM"; break;
    case 4: result="LOW"; break;
    case 5: result="TRIVIAL"; break;
  }
  return result;
}
