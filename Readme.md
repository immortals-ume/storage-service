# Storage Service using AWS S3 

# Summary of Amazon s3

Amazon Simple Storage Service is an infinite-scale object storage service.

Concepts Of AWS S3:

    Buckets: These are directories and have a globally unique name

    Objects: These are files that have a key and this key is the full path. For example s3://my-bucket/my-file.txt
    The maximum size of an object is 5TB and if the upload is larger than 5GB the multipart upload must be used.

    
    Versioning: Enabled at the bucket level. The same key overwrites and increments the version: 1, 2, 3… It’s the best
    practice to version your files.

    Under “Properties” of the bucket, you can enable this.

    S3 can also maintain static websites and make them available on the internet. If the return is an HTTP 403 (forbidden),
    it’s good to look at the policy and make sure it allows public access.

## S3 Storage Classes

# Amazon S3 Standard

    This class is used for general purposes;
    99.999999999% object resiliency across multiple Availability Zones;
    Used for data that will be accessed frequently.

# Amazon S3 Standard — Infrequent Access

    Used for data that has a lower frequency of access but requires fast access when needed;
    99.99% availability;
    Lower cost compared to S3 Standard;
    Used for disaster recovery backups.

# Amazon S3 One Zone

    Used for infrequently accessed data;
    It has a durability of 99.999999999% in a single AZ (availability zone);
    Used to store a secondary backup of copies of on-premises data, or data that can be recreated.

# Amazon S3 Glacier Instant Retrieval

    Class used to archive things, so it has a low cost;
    Data storage here must be at least 90 days long;
    Millisecond recovery is great for data accessed once a quarter.

# Amazon S3 Glacier Flexible Retrieval

    It’s also a class used to archive things, so it’s low cost;
    Data storage here must also be at least 90 days long;
    Suitable for data that can be accessed 1 or 2 times a year and retrieved asynchronously;
    Recovery times from minutes to hours.

# Amazon S3 Glacier Deep Archive

    It has a data recovery time of up to 12 hours, being suitable for data that can be accessed 1 or 2 times a year;
    Provides 99.999999999% object resiliency across multiple Availability Zones;
    Data recovery can take up to 12 hours.

# Amazon S3 Intelligent Tiering

    This is a very interesting case. This storage class automatically reduces storage costs by automatically moving data to
    the most cost-effective tier based on the frequency of access.
    
    For example, an object can be moved to a low access layer, for reasons of low access in everyday life. If this object is
    later accessed, it will be moved back to the frequently accessed tier.
    
    The durability of 99.999999999% of objects in multiple Availability Zones;
    It has a small monthly charge for monitoring and automatic levels;
