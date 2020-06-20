<# https://cloud.google.com/sdk/gcloud/reference/compute/ #>

clear

<# Delete Image #>
Write-Output "**** DELETE IMAGE ****"
gcloud compute images delete image-with-ocr --quiet

<# Create image #>
Write-Output "`n**** CREATE IMAGE ****"
gcloud compute images create image-with-ocr `
 --source-disk=ocr-free-final-project `
 --source-disk-zone=europe-north1-a


<# DELETE Instance Template #>
Write-Output "`n**** TEMPORARILY UPDATE INSTANCE GROUP WITH A DUMMY TEMPLATE ****"
gcloud compute instance-groups managed set-instance-template ocr-premium-final-project `
 --template=dummy-instance-template `
 --zone=europe-north1-a

Write-Output "`n**** DELETE INSTANCE TEMPLATE ****"
gcloud compute instance-templates delete ocr-premium-template --quiet


<# CREATE Instance Template #>
Write-Output "`n**** CREATE INSTANCE TEMPLATE ****"
gcloud compute instance-templates create ocr-premium-template `
 --machine-type=f1-micro `
 --service-account=all-services@g01-li61n.iam.gserviceaccount.com `
 --image=image-with-ocr `
 --metadata-from-file=startup-script=startupOCR.sh `
--scopes=bigquery,cloud-platform,cloud-source-repos,cloud-source-repos-ro,compute-ro,compute-rw,datastore,logging-write,monitoring,monitoring-write,pubsub,service-control,service-management,sql-admin,storage-full,storage-ro,storage-rw,taskqueue,trace,userinfo-email


<# Update Instance Group #>
Write-Output "`n**** UPDATE INSTANCE GROUP ****"
gcloud compute instance-groups managed set-instance-template ocr-premium-final-project `
 --template=ocr-premium-template `
 --zone=europe-north1-a


 

