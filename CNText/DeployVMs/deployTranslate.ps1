<# https://cloud.google.com/sdk/gcloud/reference/compute/ #>

clear

<# Delete Image #>
Write-Output "**** DELETE IMAGE ****"
gcloud compute images delete image-with-translate --quiet

<# Create image #>
Write-Output "`n**** CREATE IMAGE ****"
gcloud compute images create image-with-translate `
 --source-disk=translate-free-final-project `
 --source-disk-zone=europe-north1-a


<# DELETE Instance Template #>
Write-Output "`n**** TEMPORARILY UPDATE INSTANCE GROUP WITH A DUMMY TEMPLATE ****"
gcloud compute instance-groups managed set-instance-template translate-premium-final-project `
 --template=dummy-instance-template `
 --zone=europe-north1-a

Write-Output "`n**** DELETE INSTANCE TEMPLATE ****"
gcloud compute instance-templates delete translate-premium-template --quiet


<# CREATE Instance Template #>
Write-Output "`n**** CREATE INSTANCE TEMPLATE ****"
gcloud compute instance-templates create translate-premium-template `
 --machine-type=f1-micro `
 --service-account=all-services@g01-li61n.iam.gserviceaccount.com `
 --image=image-with-translate `
 --metadata-from-file=startup-script=startupTranslate.sh `
--scopes=bigquery,cloud-platform,cloud-source-repos,cloud-source-repos-ro,compute-ro,compute-rw,datastore,logging-write,monitoring,monitoring-write,pubsub,service-control,service-management,sql-admin,storage-full,storage-ro,storage-rw,taskqueue,trace,userinfo-email


<# Update Instance Group #>
Write-Output "`n**** UPDATE INSTANCE GROUP ****"
gcloud compute instance-groups managed set-instance-template translate-premium-final-project `
 --template=translate-premium-template `
 --zone=europe-north1-a


 

