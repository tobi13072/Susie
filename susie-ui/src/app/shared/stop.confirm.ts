export function confirmStop(stopItem: String, removeFunction: Function){
  return{
    header: `Are you sure you want stop this ${stopItem}?`,
    message: `This will stop this ${stopItem}. You cannot undo this action.`,
    icon: 'pi pi-exclamation-triangle',
    acceptLabel: "Stop",
    rejectLabel: "Cancel",
    acceptIcon: 'pi',
    rejectIcon: 'pi',
    accept: removeFunction
  }
}
