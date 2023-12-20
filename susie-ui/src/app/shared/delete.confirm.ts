export function confirmDeletion(deletedItem: String, removeFunction: Function){
  return{
    header: `Are you sure you want delete this ${deletedItem}?`,
    message: `This will delete this ${deletedItem} permanently. You cannot undo this action.`,
    icon: 'pi pi-exclamation-triangle',
    acceptLabel: "Delete",
    rejectLabel: "Cancel",
    acceptIcon: 'pi',
    rejectIcon: 'pi',
    accept: removeFunction
  }
}
