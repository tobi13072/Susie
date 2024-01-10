export function errorDialog(err: any){
  return {
    message: typeof err === 'string' ? err : err.error.message,
    header: 'Error',
    icon: 'pi pi-exclamation-triangle',
    acceptVisible: false,
    rejectLabel: "OK",
    rejectIcon: 'pi'
  };
}
